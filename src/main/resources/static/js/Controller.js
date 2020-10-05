var app = angular.module('myapp',['ngRoute']);
    app.config(function($routeProvider,$locationProvider) {
    
        $routeProvider
        .when("/events", {
            templateUrl : '/event.html',
            controller : 'eventController'
        });
   
    });
    app.controller('eventController',function($http,$scope){
        alert("http://localhost:8080/events");
     /**************************************************/
   $scope.image = null;
   $http.defaults.headers.post["Content-Type"] = "application/json";
   var url = '/events';
   var imageCopy = null;
   var image = null;
   var handleImageSelect = function (evt)
   {
       var files = evt.target.files;
       var file = files[0];
       if (files && file){
           var reader = new FileReader();
           reader.onload = function (readerEvt) {
               var binaryString = readerEvt.target.result;
               imageCopy = btoa(binaryString);
               image = 'data:image/octet-stream;base64,' + imageCopy;
               $scope.image = image;
               console.log($scope.image);
           };
       reader.readAsBinaryString(file);
       if (window.File && window.FileReader && window.FileList && window.Blob) {
           document.getElementById('filePickerImage').addEventListener('change', handleImageSelect, false);
        } else {
           alert('The File APIs are not fully supported in this browser.');
       }
       }
   };

   $scope.addproduct = function(){
        var url = "/events";
        var product = $scope.pro;
        var customerProcess = $http.post(url,product).then(function(response){
            alert("Product Added "); 
       console.log(product);
        
 });
 };

        //retrieving product list
      $http.get('/events').then(function(response){
        $scope.product = response.data;
});

/******product*****/
   $scope.saveProd = function (){
       $http.post(url, {
           name:  $scope.name,
           description: $scope.description,
           contactPerson:$Scope.contactPerson,
           province: $Scope.province,
           price: $scope.price,
           image: $scope.image
         

       }).then(successCallback, errorCallback);
          function successCallback(response) {
               alert("Product Added Successfully");
               console.log(response.data);
               
           }
           function errorCallback(response) {
               console.log(response);
               alert("failed to add Product");
           };        
   };
    });