from flask import Blueprint, jsonify
from models.estudiante import Estudiante
from models.test_realizado import testRealizados
from models.resultados import Resultados
from models.coordenadas import Coordenada
from models.distritos import Distrito
from utils.db import db

heatmap_bp = Blueprint('heatmap', __name__)

@heatmap_bp.route('/heatmap', methods=['GET'])
def get_heatmap_data():
    try:
        # Imprimir la consulta generada
        query = db.session.query(
            Estudiante.nombre,
            Estudiante.departamento_id,
            Estudiante.provincia_id,
            Estudiante.distrito_id,
            Resultados.resultado,
            Coordenada.latitude,
            Coordenada.longitude
        ).join(testRealizados, Estudiante.usuarioid == testRealizados.usuarioid)\
         .join(Resultados, testRealizados.test_id == Resultados.test_id)\
         .join(Coordenada, Estudiante.distrito_id == Coordenada.distrito_id) 

        print(str(query))  # Imprime la consulta SQL generada
        
        estudiantes = query.all()

        # Verificar si se obtuvieron resultados
        if not estudiantes:
            print("No se encontraron resultados en la consulta.")

        lista_resultados = []
        for estudiante in estudiantes:
            resultado_dict = {
                "nombre": estudiante.nombre,
                "departamento_id": estudiante.departamento_id,
                "provincia_id": estudiante.provincia_id,
                "distrito_id": estudiante.distrito_id,
                "latitude": estudiante.latitude,
                "longitude": estudiante.longitude,
                "nivel_ansiedad": estudiante.resultado
            }
            lista_resultados.append(resultado_dict)

        # Preparar la respuesta
        data = {
            'message': 'Lista de resultados',
            'status': 200,
            'data': lista_resultados
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener resultados', 'error': str(e)}), 500



        