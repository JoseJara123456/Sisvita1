from flask import Blueprint, jsonify, request
from models.tests import Tests
from models.preguntas import Preguntas
from models.opcion import Opcion 
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
            lista_preguntas = []
            
            for pregunta in preguntas:
                pregunta_data = pregunta.serialize()
                lista_preguntas.append(pregunta_data)
            
            # Obtener las opciones asociadas a este test
            opciones = Opcion.query.filter_by(tipotest_id=test.tipotest_id).all()
            lista_opciones = [opcion.serialize() for opcion in opciones]
            
            # Agregar las opciones al test_data
            test_data['opciones'] = lista_opciones
            
            # Agregar las preguntas al test_data
            test_data['preguntas'] = lista_preguntas
            
            lista_tests.append(test_data)

        # Preparar la respuesta
        data = {
            'message': 'Lista de tests y preguntas con opciones',
            'status': 200,
            'data': lista_tests
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener tests y preguntas', 'error': str(e)}), 500


