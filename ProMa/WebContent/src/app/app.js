/**
* app Module
*
* Description
*/
angular.module('app', [
	'ngRoute',
	'mgcrea.ngStrap',
	'ngAnimate',
	'chart.js'
	])
	.config(function($routeProvider,$locationProvider) {
		$routeProvider
			.when('/',
			{
				controller:'overviewController',
				templateUrl:'src/views/overview.html'
			})
			.when('/bu/:buid/:buname',
			{
				controller:'individualController',
				templateUrl:'src/views/individual.html'
			})
			.when('/project/:projectid',
			{
				controller:'projectController',
				templateUrl:'src/views/project.html'
			})
			.otherwise({redirectTo: '/'});
	})
	.controller('overviewController', function($scope,$http,$location,$alert,$timeout,$rootScope,$window){
		
		var initO = function(){
			var sessionDetailsURL = "rest/accounts/getSessionDetails";
			var sessionPromise = $http.get(sessionDetailsURL);
			sessionPromise.then(function(response){
				$rootScope.role=response.data.role;
				$rootScope.loggedin=response.data.loggedin;
			},function(response){
				$window.location.href = 'http://localhost:8080/ProMa/login.html';

			})

			var url = "rest/projects/status";
			var promise = $http.get(url);
			promise.then(function(response){
				$scope.projects = response.data;
				col={green:0,yellow:0,red:0,black:0};
			    $scope.pielabels = ["Danger", "Neutral", "OK","Stagnant"];
	  			for (var i = 0; i < $scope.projects.length; i++) {
	  				col.green += $scope.projects[i].green;
	  				col.yellow += $scope.projects[i].yellow;
	  				col.red += $scope.projects[i].red;
	  				col.black += $scope.projects[i].black;
	  			}
	  			$scope.piedata = [col.red, col.yellow, col.green,col.black];
	  			$scope.colours =  [ '#e74c3c', '#f39c12','#18bc9c','#000'] ;
			})		
			
			var roleurl = "rest/roles"
			var rolepromise = $http.get(roleurl);
			rolepromise.then(function(response){
				$scope.roles = response.data;
			})
			
			var usersUrl = "rest/users"
			var usersPromise = $http.get(usersUrl);
			usersPromise.then(function(response){
				$scope.users = response.data;
			})


			var visitorsUrl = "rest/users/visitors"
			var visitorsPromise = $http.get(visitorsUrl);
			visitorsPromise.then(function(response){
				$scope.visitors = response.data;
			})

			var adminsUrl = "rest/users/admins"
			var adminsPromise = $http.get(adminsUrl);
			adminsPromise.then(function(response){
				$scope.admins = response.data;
			})
			$timeout(function(){

				$scope.$apply();
			})

		}
		initO();
		setInterval(initO,2000);

		$scope.convertAnAdmin = function(){
			var admindata = $scope.fields;
			var convertadminUrl = "rest/users/convertToVisitor";
			var convertadminpromise = $http.post(convertadminUrl,admindata);			
			convertadminpromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Admin Priviledges Revoked', placement: 'top-right', type: 'success', show: true});
				initO();
			})
		};

		$scope.convertAnUser = function(){
			var userdata = $scope.candidateUser;
			var convertUserUrl = "rest/users/convertToAdmin";
			var convertuserpromise = $http.post(convertUserUrl,userdata);			
			convertuserpromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Admin Priviledges Granted', placement: 'top-right', type: 'success', show: true});
				initO();
				// console.log('User Converted woohoo')
			})
		}; 
		
		$scope.addABU =  function(){
			var budata = $scope.fields;
			var addBUUrl = "rest/bus";
			addBUPromise = $http.post(addBUUrl,budata);
			addBUPromise.then(function(response){
				console.log(response.data);
				$alert({duration:3,container:'body', content: 'BU Added', placement: 'top-right', type: 'success', show: true});
				
				initO();
			});

		};

	})
	.controller('individualController', function($scope,$http,$routeParams,$alert,$modal,$timeout,$rootScope,$window){
		$scope.buname = $routeParams.buname;
		
		var initI = function() {

		var sessionDetailsURL = "rest/accounts/getSessionDetails";
			var sessionPromise = $http.get(sessionDetailsURL);
			sessionPromise.then(function(response){
				$rootScope.role=response.data.role;
				$rootScope.loggedin=response.data.loggedin;
			},function(response){
				$window.location.href = 'http://localhost:8080/ProMa/login.html';
			})


			var url = "rest/projects/bus/" + $routeParams.buid;
			var promise = $http.get(url);
			promise.then(function(response){
				$scope.projects = response.data;
			})		
			$scope.buid = $routeParams.buid

			var buDetailsURL = "rest/bus/" + $scope.buid;
			var buDetailPromise = $http.get(buDetailsURL);
			buDetailPromise.then(function(response){
				$scope.buDetails = response.data;
			})
			var clientsUrl = "rest/clients"
			var clientsPromise = $http.get(clientsUrl);
			clientsPromise.then(function(response){
				$scope.clients = response.data;
			})

			var usersUrl = "rest/users"
			var usersPromise = $http.get(usersUrl);
			usersPromise.then(function(response){
				$scope.users = response.data;
			});

			$timeout(function(){

				$scope.$apply();
			})

		}
		initI();
		setInterval(initI,2000);

		$scope.addAProject = function(){
			var projectData = $scope.fields;
			var addProjectURL = "rest/projects"
			var addProjectPromise = $http.post(addProjectURL,projectData);
			addProjectPromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Project Added', placement: 'top-right', type: 'success', show: true});
				
			})

		}

		var addBUHeadModal = $modal({template:'src/views/modals/admin-add-bu-head-form.html', show:false});

		$scope.showBUHeadModal = function(){
		    addBUHeadModal.$promise.then(addBUHeadModal.show);
		}

		$scope.addABUHead = function(){
			var buHead = $scope.fields;
			var buHeadURL = "rest/bus/" + $scope.buid + "/buheads"
			var buHeadPromise = $http.post(buHeadURL,buHead);
			buHeadPromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Bu Head Added', placement: 'top-right', type: 'success', show: true});
				
			})
		}

		$scope.addClient = function(){
			var clientData = $scope.fields;
			var addClientURL = "rest/clients"
			var addClientPromise = $http.post(addClientURL,clientData);
			addClientPromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Client Added', placement: 'top-right', type: 'success', show: true});
			})
		}
	})
	.controller('projectController', function($scope,$http,$routeParams,$alert,$modal,$timeout,$rootScope,$window){
		$scope.projectid = $routeParams.projectid;
		
		var sessionDetailsURL = "rest/accounts/getSessionDetails";
			var sessionPromise = $http.get(sessionDetailsURL);
			sessionPromise.then(function(response){
				$rootScope.role=response.data.role;
				$rootScope.loggedin=response.data.loggedin;
			}
				,function(response){
				$window.location.href = 'http://localhost:8080/ProMa/login.html';
			})
		
		var initP = function(){			
			var sprintURL = "rest/projects/"+ $scope.projectid +"/sprints";
			var sprintPromise = $http.get(sprintURL);
			sprintPromise.then(function(response){
				$scope.sprints = response.data;
				$scope.currentSprint = $scope.sprints.splice(-1)[0];
			})
			
			var projectURL = "rest/projects/"+ $scope.projectid;
			var projectPromise = $http.get(projectURL);
			projectPromise.then(function(response){
				$scope.projectDetails = response.data;
			})
			
			var dataPointsURL = "rest/projects/"+ $scope.projectid + "/getDataPoints";
			var dataPointsPromise = $http.get(dataPointsURL);
			dataPointsPromise.then(function(response){
				$scope.data = response.data;
				$scope.labels = [""];
				for (var i = 1; i < $scope.data.length; i++) {
					$scope.labels.push("Sprint-"+i);
				}
			})

			var clientsUrl = "rest/clients"
			var clientsPromise = $http.get(clientsUrl);
			clientsPromise.then(function(response){
				$scope.clients = response.data;
			})

			var usersUrl = "rest/users"
			var usersPromise = $http.get(usersUrl);
			usersPromise.then(function(response){
				$scope.users = response.data;
			})
			$timeout(function(){

				$scope.$apply();
			})
		}
		initP();
		setInterval(initP,2000);
		
		$scope.addASprint = function() { 
			var addSprintURL = "rest/projects/" + $scope.projectid + "/sprints";
			var sprintData = $scope.fields;
			$http.post(addSprintURL,sprintData).then(function(response){
				console.log(response)
				$scope.projectDetails=null;
				//initP();
				$alert({duration:3,container:'body', content: 'Sprint Added', placement: 'top-right', type: 'success', show: true});
				
			})

		}

		$scope.closeSprint = function(){
			var closeSprintURL = "rest/projects/closeSprint";
			$http.put(closeSprintURL,$scope.projectDetails).then(function(response){
				console.log(response)
				//initP();
				});
	
		}

		$scope.editAProject = function(){
			var editProjectURL = "rest/projects/" + $scope.projectid;
			var editProjectDetails = $scope.editfields;
			var editProjectPromise = $http.put(editProjectURL,editProjectDetails);
			editProjectPromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Project Details Edited', placement: 'top-right', type: 'success', show: true});
			})
		}
		$scope.editCurrentSprint = function(){
			var editSprintURL = "rest/projects/" + $scope.projectid +"/sprints/" + $scope.currentSprint.sprint_id;
			var editSprintDetails = $scope.editsprintfields;
			var editSprintPromise = $http.put(editSprintURL,editSprintDetails);
			editSprintPromise.then(function(response){
				$alert({duration:3,container:'body', content: 'Current Sprint Edited', placement: 'top-right', type: 'success', show: true});
			})
		}
	  $scope.series = ['Series A'];
	  // $scope.data = [
	  //   [65, 59, 80, 81, 56, 55, 40],
	  // ];
	  $scope.onClick = function (points, evt) {
	    console.log(points, evt);
	  }

	 	  
	  // $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
	  // $scope.options = {
	  //   scales: {
	  //     yAxes: [
	  //       {
	  //         id: 'y-axis-1',
	  //         type: 'linear',
	  //         display: true,
	  //         position: 'left'
	  //       },
	  //       {
	  //         id: 'y-axis-2',
	  //         type: 'linear',
	  //         display: true,
	  //         position: 'right'
	  //       }
	  //     ]
	  //   }
	  //};


	})