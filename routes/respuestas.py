from flask import Blueprint, jsonify, request
from models.test_realizado import testRealizados
from models.opcion import Opcion
from utils.db import db
from datetime import datetime

respuestas_bp = Blueprint('respuestas', __name__)

@respuestas_bp.route('/enviarRespuestas/<int:usuario_id>', methods=['POST'])
def enviar_respuestas(usuario_id):
    try:
        data = request.json
        respuestas = data.get('respuestas', [])

        total_puntaje = 0

        # Iterar sobre las respuestas recibidas
        for respuesta in respuestas:
            opcion_id = respuesta['opcionId']

            # Obtener el valor del puntaje según la opcionId
            puntaje = obtener_puntaje_por_opcion_id(opcion_id)

            # Sumar al total de puntaje
            total_puntaje += puntaje

        # Obtener tipotest_id de la primera opción (suponiendo que todas tienen el mismo tipotest_id)
        primer_opcion_id = respuestas[0]['opcionId']
        opcion = Opcion.query.filter_by(opcionid=primer_opcion_id).first()
        tipotest_id = opcion.tipotest_id if opcion else None

        # Guardar en la tabla `test_realizados` una vez, fuera del bucle
        test_realizado = testRealizados(
            fecha_test=datetime.now(),
            tipotest_id=tipotest_id,
            usuarioid=usuario_id,
            puntaje=total_puntaje
        )
        db.session.add(test_realizado)
        db.session.commit()

        # Preparar la respuesta
        data = {
            'message': 'Respuestas enviadas correctamente',
            'status': 200,
            'total_puntaje': total_puntaje
        }

        return jsonify(data), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Error al procesar las respuestas', 'error': str(e)}), 500

def obtener_puntaje_por_opcion_id(opcion_id):
    # Definir el mapeo de puntajes según la opcionId
    puntajes = {
        1: 0,
        2: 1,
        3: 2,
        4: 3,
        5: 0,
        6: 1,
        7: 2,
        8: 3,
        9: 4,
        10: 0,
        11: 1,
        12: 2,
        13: 3
    }

    # Obtener el puntaje correspondiente a la opcionId
    return puntajes.get(opcion_id, 0)  # Retorna 0 si no se encuentra en el diccionario