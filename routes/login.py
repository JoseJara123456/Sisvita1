from flask import Blueprint, jsonify
from models.usuarios import Usuarios
from utils.db import db
from datetime import datetime

# Crear un Blueprint para las rutas de autenticación
login_bp = Blueprint('usuarios', __name__)

@login_bp.route('/registrar_usuario', methods=['GET'])
def registrar_usuario_prueba():
    # Datos de ejemplo para el usuario
    datos_usuario = {
        'usuarioid': 17,  # Proporciona un valor para UsuarioID
        'nombre': 'Ejemplo4 Usuario',
        'email': 'ejemplo4@usuario.com',
        'password': 'contraseña12345',
        'rol': 'Estudiante'
    }

    # Crear una nueva instancia de Usuarios con los datos de ejemplo
    nuevo_usuario = Usuarios(usuarioid=datos_usuario['usuarioid'],
                             nombre=datos_usuario['nombre'],
                             email=datos_usuario['email'],
                             password=datos_usuario['password'],
                             rol=datos_usuario['rol'])

    try:
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
