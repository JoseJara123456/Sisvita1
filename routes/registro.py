from flask import Blueprint, jsonify, request
from models.usuarios import Usuarios
from utils.db import db
from datetime import datetime
from werkzeug.security import generate_password_hash

# Crea un Blueprint para la autenticación
registro_bp = Blueprint('registrar_usuarios', __name__)

@registro_bp.route('/registrar_usuarios', methods=['POST'])
def registro_usuario():
    try:
        datos_usuario = request.get_json()

        if Usuarios.query.filter_by(email=datos_usuario['email']).first():
            return jsonify({'message': 'El correo electrónico ya está en uso'}), 400
        
        if Usuarios.query.filter_by(usuarioid=datos_usuario['usuarioid']).first():
            return jsonify({'message': 'El ID de usuario ya está en uso'}), 400

        nuevo_usuario = Usuarios(
            usuarioid=datos_usuario['usuarioid'],  # Asegúrate de incluir esto
            nombre=datos_usuario['nombre'],
            email=datos_usuario['email'],
            password=generate_password_hash(datos_usuario['password'], method='pbkdf2'),
            #password=datos_usuario['password'],
            rol=datos_usuario['rol']
        )

        db.session.add(nuevo_usuario)
        db.session.commit()

        resultado = {
            'usuarioid': nuevo_usuario.usuarioid,
            'nombre': nuevo_usuario.nombre,
            'email': nuevo_usuario.email,
            'rol': nuevo_usuario.rol
        }
        data = {
            'message': 'Nuevo usuario registrado exitosamente',
            'status': 201,
            'data': resultado
        }
        return jsonify(data), 201

    except Exception as e:
        db.session.rollback()
        print("Error:", e)
        return jsonify({'message': 'Error al registrar usuario', 'error': str(e)}), 500