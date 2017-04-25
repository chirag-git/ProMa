angular.module('app')
	.service('projectService', function($http){
		
		getSprintsForProject = function(projectid){
			var sprintURL = "rest/projects/" + projectid + "/sprints";
			var sprintPromise = $http.get(sprintURL);
			sprintPromise.then(function(response) {
				return response.data;
			})
		}

		getProjectDetails = function(projectid){
			var projectURL = "rest/projects/" + projectid;
			var projectPromise = $http.get(projectURL);
			projectPromise.then(function(response) {
				return response.data;
			})
		}


	})