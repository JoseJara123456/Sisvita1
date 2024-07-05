from flask import Blueprint, jsonify, request
from models.diagnostico import Diagnostico  # Asegúrate de importar tu modelo Diagnostico
from models.especialista import Especialista  # Asegúrate de importar tu modelo Especialista
from utils.db import db
from datetime import datetime
from flask import flash, session, jsonify
from flask_mail import Message
from utils.mail import mail_instance

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

        # Obtener nombre del especialista
        especialista = Especialista.query.filter_by(usuarioid=usuario_id).first()
        if not especialista:
            return jsonify({'message': 'No se encontró especialista con el usuario ID proporcionado'}), 404

        nombre_especialista = especialista.nombre

        
        enviar_correo(nombre_especialista,test_id, tratamiento, diagnostico_nivel_ansiedad, fundamentacion_cientifica, observaciones)

        

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

def enviar_correo(nombre_especialista, test_id, tratamiento, diagnostico_nivel_ansiedad, fundamentacion_cientifica, observaciones):
    correo_usuario = 'xjoseduran01@gmail.com'
    if not correo_usuario:
        return jsonify({"error": "correo_usuario is not set in session"}), 400

    msg = Message(
        'Datos de diagnóstico',
        sender='sisvita.fisi@gmail.com',  
        recipients=[correo_usuario]       
    )
    msg.body = f'''
    <html>
    <body>
        <h2>Datos del diagnóstico</h2>
        <p><strong>Diagnóstico realizado por:</strong> {nombre_especialista}</p>
        <p><strong>Se recomienda el siguiente Tratamiento:</strong> {tratamiento}</p>
        <p><strong>Nivel de ansiedad diagnósticado:</strong> {diagnostico_nivel_ansiedad}</p>
        <p><strong>Fundamentación científica:</strong> {fundamentacion_cientifica}</p>
        <p><strong>Observaciones:</strong> {observaciones}</p>
    </body>
    </html>
    '''

    msg.html = msg.body

    try:
        mail_instance.send(msg)
        flash("Correo enviado correctamente")
        return jsonify({"message": "Correo enviado correctamente"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500
