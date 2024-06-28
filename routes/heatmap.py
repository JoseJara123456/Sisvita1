from flask import Blueprint, jsonify
from models.estudiante import Estudiante
from models.test_realizado import testRealizados
from models.ubigeo import Ubigeo
from utils.db import db

heatmap_bp = Blueprint('heatmap', __name__)

def map_nivel_ansiedad(nivel):
    mapping = {
        "leve": 1,
        "moderada": 2,
        "grave": 3
    }
    return mapping.get(nivel)

@heatmap_bp.route('/heatmap', methods=['GET'])
def get_heatmap_data():
    try:
        query = db.session.query(
            testRealizados.test_id,
            Estudiante.usuarioid,
            testRealizados.nivel_ansiedad,
            Ubigeo.ubigeoid,
            Ubigeo.latitud,
            Ubigeo.longitud
        ).join(testRealizados, Estudiante.usuarioid == testRealizados.usuarioid)\
         .join(Ubigeo, Estudiante.ubigeoid == Ubigeo.ubigeoid) 

        resultados = query.all()

        lista_resultados = []
        for resultado in resultados:
            resultado_dict = {
                "test_id": resultado.test_id,
                "usuarioid": resultado.usuarioid,
                "nivel_ansiedad": map_nivel_ansiedad(resultado.nivel_ansiedad),
                "ubigeoid": resultado.ubigeoid,
                "latitud": resultado.latitud,
                "longitud": resultado.longitud
            }
            lista_resultados.append(resultado_dict)

        data = {
            'message': 'Lista de ubicación y nivel de ansiedad de estudiantes',
            'status': 200,
            'data': lista_resultados
        }

        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener resultados', 'error': str(e)}), 500
