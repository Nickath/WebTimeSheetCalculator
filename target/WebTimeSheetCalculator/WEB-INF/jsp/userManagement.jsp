<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
  <head>  
    <title>Admin Controler Panel </title>  
    <style>
      .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }
      
      .role.ng-valid {
          background-color: lightgreen;
      }
      .role.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .role.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }
 
      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }
      
      
 
    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/resources/css/main.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
  <jsp:include page="navBar.jsp" />
      <div class="generic-container" ng-controller="UserController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Admin Panel - Update / Insert User Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.user.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="uname">Username</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.username" id="uname" class="username form-control input-sm" placeholder="Enter user's name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                         
                       
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="role">Role</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.role.role" id="role" class="form-control input-sm" placeholder="Enter the User Role [This field is validation free]" required ng-minlength="7"/>
                                   <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.role.role.$error.required">This is a required field</span>
                                      <span ng-show="myForm.role.role.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
 
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="email">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter user's  Email" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">This is a required field</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
 
                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Users </span></div>
              <div class="tablecontainer">
              <!--  <table class="table table-hover"> -->
                 <table ng-table="vm.tableParams" class="table" show-filter="true">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>UserName</th>
                              <th>Email</th>
                              <th>Role</th>  
                              <th>Enabled</th>                              
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.users">
                              <td title="'id'" filter="{ id: 'number'}" sortable="'u.id'"><span ng-bind="u.id"></span></td>
                              <td title="'username'" filter="{ username: 'text'}" sortable="'u.username'"><span ng-bind="u.username"></span></td>
                              <td title="'email'" filter="{ email: 'text'}" sortable="'u.email'"><span ng-bind="u.email"></span></td>
                              <td title="'role'" filter="{ role: 'number'}" sortable="'u.role.role'"><span ng-bind="u.role.role"></span></td>
                              <td title="'enabled'" filter="{ enabled: 'boolean'}" sortable="'u.enabled'"><span ng-bind="u.enabled"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
       
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/resources/js/angular/app.js' />"></script>
      <script src="<c:url value='/resources/js/angular/user_service.js' />"></script>
      <script src="<c:url value='/resources/js/angular/user_controller.js' />"></script>
      <link rel="stylesheet"; href="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.css">

      <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
      <script src="angular-datatables.min.js"></script>
      <script src="jquery.dataTables.min.js"></script>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
      <link rel="stylesheet" href="datatables.bootstrap.css">
      <script src="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.js"></script>
  
  <jsp:include page="footer.jsp" />
  </body>
</html>