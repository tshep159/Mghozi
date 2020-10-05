(function(){
    'use strict';
    angular
            .module('app')
            .controller('PostsController',PostsController);
            
         PostsController.$inject=[];
         function PostsController(){
             var vm = this;
             
             vm.posts=[];
             vm.getAll = getAll;
             vm.deletePost = deletePost;
             init();
             
             function getAll(){
                 var url ="/posts/all";
                 var postsPromise = $http.get(url);
                 postsPromise.then(function(response){
                     vm.posts = response.data;
                });
             }
             
             
             
             function deletePost(){
                 var url ="/delete/post/"+id;
                 $http.post(url).then(function(response){
                     vm.posts = response.data;
                 });
             }    
         }
})();