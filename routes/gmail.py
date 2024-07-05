from flask import Blueprint,render_template, flash, session
from flask_mail import Mail, Message
from utils.mail import mail_instance
from flask import render_template
from flask import Blueprint


gmail_bp = Blueprint('usuarios', __name__)
    
@gmail_bp.route('/enviarCorreo', methods=['POST'])

def enviar_correo(diagnostico_id):
    
    
    correo_usuario = session.get('correo_usuario')
    msg = Message('Su diagnostico se ha realizado', sender='Sisvita@gmail.com', recipients=[correo_usuario])
    msg.html = render_template('email.html')

    mail_instance.send(msg) 
    flash("Diagnosticado correctamente")
    
    return 