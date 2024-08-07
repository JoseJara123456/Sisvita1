from flask import Flask
from routes.login import login_bp
from routes.registro import registro_bp
from routes.test import test_bp
from routes.resultadostest import testrealizado_bp
from routes.test_fecha import test_fecha_id_bp
from routes.respuestas import respuestas_bp
from routes.heatmap import heatmap_bp
from routes.diagnosticos import diagnosticos_bp
from routes.gmail import gmail_bp
from flask_sqlalchemy import SQLAlchemy
from config import DATABASE_CONNECTION_URI
from flask_cors import CORS
from utils.db import db
from utils.mail import mail_instance, configure_mail
app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

app.secret_key = 'clavesecreta123'

app.config["SQLALCHEMY_DATABASE_URI"] = DATABASE_CONNECTION_URI
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_POOL_SIZE"] = 20
app.config["SQLALCHEMY_POOL_TIMEOUT"] = 30
app.config["SQLALCHEMY_POOL_RECYCLE"] = 1800

# no cache
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0


configure_mail(app)

db.init_app(app)

app.register_blueprint(login_bp)
app.register_blueprint(registro_bp)
app.register_blueprint(test_bp)
app.register_blueprint(testrealizado_bp)
app.register_blueprint(test_fecha_id_bp)
app.register_blueprint(respuestas_bp)
app.register_blueprint(heatmap_bp)
app.register_blueprint(diagnosticos_bp)
app.register_blueprint(gmail_bp)

if __name__ == '__main__':
  app.run(port=5000)
