from flask import Blueprint, jsonify, request
from models.usuarios import Usuarios
from utils.db import db
from datetime import datetime

# Crear un Blueprint para las rutas de autenticación
login_bp = Blueprint('usuarios', __name__)

@login_bp.route('/registrar_usuario', methods=['POST'])
def registrar_usuario_prueba():
    try:
        # Obtener datos del request
        datos_usuario = request.get_json()

        # Validar si los datos necesarios están presentes en la solicitud
        if not datos_usuario or not all(key in datos_usuario for key in ('usuarioid', 'nombre', 'email', 'password', 'rol')):
            return jsonify({'message': 'Datos insuficientes'}), 400

        # Crear una nueva instancia de Usuarios con los datos proporcionados
        nuevo_usuario = Usuarios(usuarioid=datos_usuario['usuarioid'],
                                 nombre=datos_usuario['nombre'],
                                 email=datos_usuario['email'],
                                 password=datos_usuario['password'],
                                 rol=datos_usuario['rol'])

        # Agregar la nueva instancia a la base de datos
        db.session.add(nuevo_usuario)
        db.session.commit()

        # Preparar la respuesta
        resultado = {
            'usuarioid': nuevo_usuario.usuarioid,
            'nombre': nuevo_usuario.nombre,
            'email': nuevo_usuario.email,
            'rol': nuevo_usuario.rol
        }
        data = {
            'message': 'Nuevo usuario agregado',
            'status': 201,
            'data': resultado
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 201

    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Error al agregar usuario', 'error': str(e)}), 500
    
    
@login_bp.route('/Login', methods=['GET'])
def obtener_usuarios():
    try:
        usuarios = Usuarios.query.all()
        lista_usuarios = []
        for usuario in usuarios:
            usuario_data = {
                'email': usuario.email,
                'password': usuario.password
            }
            lista_usuarios.append(usuario_data)

        # Preparar la respuesta
        data = {
            'message': 'Lista de usuarios',
            'status': 200,
            'data': lista_usuarios
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener usuarios', 'error': str(e)}), 500










@login_bp.route('/Login2', methods=['POST'])
def login2():
    try:

        data = request.get_json()
        if not data or not data.get('email') or not data.get('password'):
            return jsonify({'message': 'Email y password son requeridos'}), 400

        email = data.get('email')
        password = data.get('password')

        
        usuario = Usuarios.query.filter_by(email=email).first()
        if usuario and usuario.password == password:
            # Autenticación exitosa
            response = {
                'message': 'Inicio de sesión exitoso',
                'status': 200,
                'data': {
                    'email': usuario.email
                }
            }
            return jsonify(response), 200
        else:
            # Autenticación fallida
            return jsonify({'message': 'Credenciales inválidas'}), 401

    except Exception as e:
        return jsonify({'message': 'Error al iniciar sesión', 'error': str(e)}), 500
