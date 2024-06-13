from flask import Blueprint, jsonify, request
from models.tests import Tests
from models.preguntas import Preguntas
from utils.db import db
from datetime import datetime


test_bp = Blueprint('test', __name__)

@test_bp.route('/VerTest', methods=['GET'])
def vertest():
    try:
        # Obtener todos los tests
        tests = Tests.query.all()
        
        lista_tests = []
        
        for test in tests:
            test_data = test.serialize()
            
            # Obtener las preguntas asociadas a este test
            preguntas = Preguntas.query.filter_by(tipotest_id=test.tipotest_id).all()
            lista_preguntas = [pregunta.serialize() for pregunta in preguntas]
            
            # Agregar las preguntas al test_data
            test_data['preguntas'] = lista_preguntas
            
            lista_tests.append(test_data)

        # Preparar la respuesta
        data = {
            'message': 'Lista de tests y preguntas',
            'status': 200,
            'data': lista_tests
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener tests y preguntas', 'error': str(e)}), 500
