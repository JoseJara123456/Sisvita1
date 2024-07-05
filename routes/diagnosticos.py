from flask import Blueprint, jsonify, request
from models.diagnostico import Diagnostico  # Asegúrate de importar tu modelo Diagnostico
from utils.db import db
from datetime import datetime

diagnosticos_bp = Blueprint('diagnostico', __name__)



@diagnosticos_bp.route('/Diagnosticos', methods=['POST'])
def datos_diagnosticos():
    try:
        data = request.json

        # Extraer datos del JSON recibido
        usuario_id = data.get('idUsuario')
        test_id = data.get('testId')
        tratamiento = data.get('tratamiento')
        diagnostico_nivel_ansiedad = data.get('diagnosticoNivelAnsiedad')
        fundamentacion_cientifica = data.get('fundamentacionCientifica')
        observaciones = data.get('observaciones')

        # Verificar que todos los campos necesarios estén presentes
        if not usuario_id or not test_id or not tratamiento or not diagnostico_nivel_ansiedad or not fundamentacion_cientifica or not observaciones:
            return jsonify({'message': 'Bad request: Faltan campos obligatorios'}), 400

        # Obtener el ID del tratamiento
        id_tratamiento = tratamientos_ids.get(tratamiento)
        if id_tratamiento is None:
            return jsonify({'message': 'Tratamiento no válido'}), 400

        

        # Crear instancia de Diagnostico
        nuevo_diagnostico = Diagnostico(
            usuarioid=usuario_id,
            test_id=test_id,
            id_tratamiento=id_tratamiento,
            nivelansiedad=diagnostico_nivel_ansiedad,
            fundamentacion=fundamentacion_cientifica,
            observacion=observaciones,
            fecha_diagnostico=datetime.now()
        )

        # Guardar en la base de datos
        db.session.add(nuevo_diagnostico)
        db.session.commit()

        # Preparar la respuesta
        response_data = {
            'message': 'Datos de diagnóstico enviados correctamente',
            'status': 200,
        }

        return jsonify(response_data), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Error al procesar la solicitud', 'error': str(e)}), 500

# Definir un diccionario de tratamientos con sus respectivos IDs
tratamientos_ids = {
    "Terapia Cognitivo-Conductual": 1,
    "Ejercicio Regular": 2,
    "Meditación/Relajación": 3,
    "Medicamentos Ansiolíticos": 4,
    "Técnicas de Respiración": 5,
    "Terapia Cognitivo-Conductual (TCC)": 6,
    "Meditación y atención plena": 7,
    "Actividad Física": 8
}