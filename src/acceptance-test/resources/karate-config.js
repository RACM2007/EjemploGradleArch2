function fn() {  

	var config = {
		baseURL : 'https://localhost'
	};

    var configVariableGroups = { urlApiVariableGroups : karate.properties['urlVariableGroup'] , token : karate.properties['tokenVariableGroup'] }
    var result = karate.callSingle('classpath:cl/bci/ecodig/acceptance/helper/call-devops-variable-groups.feature', configVariableGroups)
    var variables = result.response.value[0].variables
    for (property in variables){
            config[property] = variables[property].value
    }

	config.apimBaseUrlMs = karate.properties['apimBaseUrlMs'];
    config.defEnv = karate.properties['defEnv'];

	karate.log('The value of env is: ',config.defEnv);
	karate.configure('connectTimeout',10000);
	karate.configure('readTimeout',10000);
	karate.configure('ssl', true);
	return config;
}  
