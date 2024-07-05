from flask_mail import Mail, Message

class mail:
    SECRET_KEY = '123456'
    MAIL_SERVER = "smtp.googlemail.com"
    MAIL_PORT = 587
    MAIL_USE_TLS = True
    MAIL_USERNAME = 'Sisvita@gmail.com'
    MAIL_PASSWORD = 'htghjytuhbugcefs'
   
    

mail_instance = Mail() 

def configure_mail(app):
    mail_instance.init_app(app)