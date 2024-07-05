package com.example.myapplication.data

iimport com.example.myapplication.data.model.TestData

object SelectedTestData {
    var nombreTest: String? = null
    var nombreUsuario: String? = null
    var nivelAnsiedad: String? = null
    var testId: String? = null

    fun saveTestData(testData: TestData) {
        testId = testData.testId
        nombreTest = testData.nombreTest
        nombreUsuario = testData.nombreUsuario
        nivelAnsiedad = testData.nivelAnsiedad
    }

    fun clearTestData() {
        nombreTest = null
        nombreUsuario = null
        nivelAnsiedad = null
    }
}
