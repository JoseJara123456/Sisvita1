from flask import Blueprint, flash, session, jsonify
from flask_mail import Message
from utils.mail import mail_instance

gmail_bp = Blueprint('gmail', __name__)

@gmail_bp.route('/enviarCorreo', methods=['POST'])
def enviar_correo():
    correo_usuario = 'xjoseduran01@gmail.com'
    if not correo_usuario:
        return jsonify({"error": "correo_usuario is not set in session"}), 400

    msg = Message(
        'Mensaje de Prueba',
        sender='sisvita.fisi@gmail.com',  # Remitente del correo
        recipients=[correo_usuario]       # Destinatarios del correo
    )
    msg.body = 'Este es un mensaje de prueba.'  # Cuerpo del mensaje

    try:
        mail_instance.send(msg)
        flash("Correo enviado correctamente")
        return jsonify({"message": "Correo enviado correctamente"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500
