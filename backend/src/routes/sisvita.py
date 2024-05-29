from flask import Blueprint, jsonify, request

# Models
from models.SisvitaModel import SisvitaModel

main = Blueprint('especialista_blueprint', __name__)

@main.route('/')
def get_especialistas():
    try:
        especialistas = SisvitaModel.get_especialistas()
        return jsonify(especialistas)
    except Exception as ex:
        return jsonify({'message': str(ex)}), 500