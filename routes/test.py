from flask import Blueprint, jsonify, request
from models.tests import Tests
from models.preguntas import Preguntas
from utils.db import db
from datetime import datetime

from flask import Blueprint, jsonify, request
from models.tests import Tests
from models.preguntas import Preguntas
from utils.db import db
from datetime import datetime

test_bp = Blueprint('test', __name__)
from flask import Blueprint, jsonify, request
from models.tests import Tests
from models.preguntas import Preguntas
from utils.db import db
from datetime import datetime

test_bp = Blueprint('test', __name__)

@test_bp.route('/RealizarTest/<int:idUsuario>', methods=['GET'])
def obtener_tests_y_preguntas_usuario(idUsuario):
    try:
        # Filtrar los tests por el id del usuario (estudiante_id)
        tests = Tests.query.filter_by(estudiante_id=idUsuario).all()
        
        # Lista para almacenar los tests y sus preguntas serializadas
        lista_tests = []
        
        for test in tests:
            test_data = test.serialize()
            
            # Obtener las preguntas asociadas a este test
            preguntas = Preguntas.query.filter_by(testid=test.testid).all()
            lista_preguntas = [pregunta.serialize() for pregunta in preguntas]
            
            # Agregar las preguntas al test_data
            test_data['preguntas'] = lista_preguntas
            
            lista_tests.append(test_data)

        # Preparar la respuesta
        data = {
            'message': 'Lista de tests y preguntas del usuario',
            'status': 200,
            'data': lista_tests
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener tests y preguntas del usuario', 'error': str(e)}), 500


    