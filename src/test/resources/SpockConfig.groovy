/**
 * Optimización del orden de ejecución de las pruebas Spock
 * Spock utiliza ciertos criterios para decidir que test se ejecutan primero
 * de acuerdo a los resultados de ejecuciones previas
 */
runner {
	optimizeRunOrder true
}

/**
 * Configuración de los reportes de Spock
 *
 * Alternativamente a este archivo, esto se puede configurar en un archivo de propiedades
 * ubicado en: src/test/resources/META-INF/services/com.athaydes.spockframework.report.IReportCreator.properties
 */
spockReports {
	//Nombre del proyecto
	set 'com.athaydes.spockframework.report.projectName': "PoC de Microservicios"
	
	//Versión del proyecto
	set 'com.athaydes.spockframework.report.projectVersion': "1.0"
	
	//Flag para indicar si en el reporte se debe mostrar o no el código fuente de los tests
    set 'com.athaydes.spockframework.report.showCodeBlocks': true
	
	//Ubicación del código fuente de los test (sólo es usado si showCodeBlocks es 'true')
	set 'com.athaydes.spockframework.report.testSourceRoots': "src/test/groovy"
	
	//Directorio de salida del reporte de spock (relativa al directorio de trabajo)
    set 'com.athaydes.spockframework.report.outputDir': "build/spock-reports"
}