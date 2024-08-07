from flask import Blueprint, jsonify, request
from models.test_realizado import testRealizados
from models.opcion import Opcion
from utils.db import db
from datetime import datetime

respuestas_bp = Blueprint('respuestas', __name__)

@respuestas_bp.route('/enviarRespuestas', methods=['POST'])
def enviar_respuestas():
    try:
        data = request.json
        usuario_id = data.get('usuarioId')
        respuestas = data.get('respuestas', [])

        if not usuario_id or not respuestas:
            return jsonify({'message': 'Bad request: usuarioId o respuestas faltantes', 'status': 400}), 400

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
        if opcion is None:
            return jsonify({'message': 'Bad request: Opción no encontrada', 'status': 400}), 400

        tipotest_id = opcion.tipotest_id

        
        nivel_ansiedad = calcular_nivel_ansiedad(tipotest_id, total_puntaje)

        
        test_realizado = testRealizados(
            fecha_test=datetime.now(),
            tipotest_id=tipotest_id,
            usuarioid=usuario_id,
            puntaje=total_puntaje,
            nivel_ansiedad=nivel_ansiedad
        )
        db.session.add(test_realizado)
        db.session.commit()

        data = {
            'message': 'Respuestas enviadas correctamente',
            'status': 200,
            'nivel_ansiedad': nivel_ansiedad
        }

        return jsonify(data), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Error al procesar las respuestas', 'error': str(e)}), 500

def obtener_puntaje_por_opcion_id(opcion_id):
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

    
    return puntajes.get(opcion_id, 0)  

def calcular_nivel_ansiedad(tipotest_id, puntaje):
    if tipotest_id == 1:
        if puntaje <= 21:
            return 'leve'
        elif 22 <= puntaje <= 35:
            return 'moderada'
        else:
            return 'grave'
    elif tipotest_id == 4:
        if puntaje <= 23:
            return 'leve'
        elif 24 <= puntaje <= 47:
            return 'moderada'
        else:
            return 'grave'
    elif tipotest_id == 5:
        if puntaje <= 9:
            return 'leve'
        elif 10 <= puntaje <= 19:
            return 'moderada'
        else:
            return 'grave'
    else:
        return 'Nivel de ansiedad no definido'
