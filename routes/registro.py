from flask import Blueprint, jsonify, request
from models.usuarios import Usuarios
from models.estudiante import Estudiante
from models.especialista import Especialista
from utils.db import db
from datetime import datetime
from werkzeug.security import generate_password_hash

# Crea un Blueprint para la autenticación
registro_bp = Blueprint('registrar_usuarios', __name__)

@registro_bp.route('/registrar_estudiantes', methods=['POST'])
def registro_estudiante():
    try:
        datos_usuario = request.get_json()

        if Estudiante.query.filter_by(email=datos_usuario['email']).first():
            return jsonify({'message': 'El correo electrónico ya está en uso'}), 400

        nuevo_estudiante = Estudiante(
            nombre=datos_usuario['nombre'],
            email=datos_usuario['email'],
            password=generate_password_hash(datos_usuario['password'], method='pbkdf2'),
            rol=datos_usuario['rol']
        )

        db.session.add(nuevo_estudiante)
        db.session.commit()

        resultado = {
            'usuarioid': nuevo_estudiante.usuarioid,
            'nombre': nuevo_estudiante.nombre,
            'email': nuevo_estudiante.email,
            'rol': nuevo_estudiante.rol
        }
        data = {
            'message': 'Nuevo estudiante registrado exitosamente',
            'status': 201,
            'data': resultado
        }
        return jsonify(data), 201

    except Exception as e:
        db.session.rollback()
        print("Error:", e)
        return jsonify({'message': 'Error al registrar estudiante', 'error': str(e)}), 500

@registro_bp.route('/registrar_especialistas', methods=['POST'])
def registro_usuario():
    try:
        datos_usuario = request.get_json()

        if Especialista.query.filter_by(email=datos_usuario['email']).first():
            return jsonify({'message': 'El correo electrónico ya está en uso'}), 400

        nuevo_especialista = Especialista(
            nombre=datos_usuario['nombre'],
            email=datos_usuario['email'],
            password=generate_password_hash(datos_usuario['password'], method='pbkdf2'),
            rol=datos_usuario['rol'],
            especialidad=datos_usuario['especialidad']
        )

        db.session.add(nuevo_especialista)
        db.session.commit()

        resultado = {
            'usuarioid': nuevo_especialista.usuarioid,
            'nombre': nuevo_especialista.nombre,
            'email': nuevo_especialista.email,
            'rol': nuevo_especialista.rol,
            'especialidad':nuevo_especialista.especialidad
        }
        data = {
            'message': 'Nuevo especialista registrado exitosamente',
            'status': 201,
            'data': resultado
        }
        return jsonify(data), 201

    except Exception as e:
        db.session.rollback()
        print("Error:", e)
        return jsonify({'message': 'Error al registrar especialista', 'error': str(e)}), 500