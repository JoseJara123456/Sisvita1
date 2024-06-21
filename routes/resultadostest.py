from flask import Blueprint, jsonify, request
from models.test_realizado import testRealizados
from models.usuarios import Usuarios
from models.tests import Tests
from utils.db import db
from datetime import datetime

testrealizado_bp = Blueprint('testrealizado', __name__)

@testrealizado_bp.route('/VerTestRealizados', methods=['GET'])
def vertestrealizado():
    try:
        # Obtener todos los tests realizados desde la base de datos
        tests = testRealizados.query.all()
        
        lista_tests = []
        
        for test in tests:
            # Obtener el nombre del usuario asociado al estudiante_id
            usuario = Usuarios.query.get(test.usuarioid)
            nombre_usuario = usuario.nombre if usuario else None
            
            # Obtener el nombre del test asociado al tipotest_id
            test_obj = Tests.query.get(test.tipotest_id)
            nombre_test = test_obj.nombre if test_obj else None
            
            # Crear un diccionario para cada test realizado y serializarlo
            test_data = {
                'test_id': test.test_id,
                'fecha_test': test.fecha_test.strftime('%Y-%m-%d') if test.fecha_test else None,
                'nombre_usuario': nombre_usuario,
                'nombre_test': nombre_test,
                'puntaje': test.puntaje
            }
            lista_tests.append(test_data)
        
        # Preparar la respuesta
        data = {
            'message': 'Lista de tests realizados',
            'status': 200,
            'data': lista_tests
        }

        # Devolver la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener tests realizados', 'error': str(e)}), 500
