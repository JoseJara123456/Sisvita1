from flask import Blueprint, jsonify
from models.estudiante import Estudiante
from models.test_realizado import testRealizados
from models.resultados import Resultados
from models.ubigeo import Ubigeo
from utils.db import db

heatmap_bp = Blueprint('heatmap', __name__)

@heatmap_bp.route('/heatmap', methods=['GET'])
def get_heatmap_data():
    try:
        query = db.session.query(
            testRealizados.test_id,
            Estudiante.usuarioid,
            Resultados.resultado,
            Ubigeo.ubigeoid,
            Ubigeo.latitud,
            Ubigeo.longitud
        ).join(testRealizados, Estudiante.usuarioid == testRealizados.usuarioid)\
         .join(Resultados, testRealizados.test_id == Resultados.test_id)\
         .join(Ubigeo, Estudiante.ubigeoid == Ubigeo.ubigeoid) 

        resultados = query.all()

        lista_resultados = []
        for resultado in resultados:
            resultado_dict = {
                "test_id": resultado.test_id,
                "usuarioid": resultado.usuarioid,
                "resultado_nivel_ansiedad": resultado.resultado,
                "ubigeoid": resultado.ubigeoid,
                "latitud": resultado.latitud,
                "longitud": resultado.longitud
            }
            lista_resultados.append(resultado_dict)

        # Preparar la respuesta
        data = {
            'message': 'Lista de ubicación y nivel de ansiedad',
            'status': 200,
            'data': lista_resultados
        }

        # Devuelve la respuesta JSON
        return jsonify(data), 200

    except Exception as e:
        return jsonify({'message': 'Error al obtener resultados', 'error': str(e)}), 500
