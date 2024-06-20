from flask import Blueprint, jsonify, request
from models.test_realizado import testRealizados
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
            # Crear un diccionario para cada test realizado y serializarlo
            test_data = {
                'test_id': test.test_id,
                'fecha_test': test.fecha_test.strftime('%Y-%m-%d') if test.fecha_test else None,
                'tipotest_id': test.tipotest_id,
                'estudiante_id': test.estudiante_id,
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