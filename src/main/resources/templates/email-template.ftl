<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User Gmail page</title>
    <style>
        *{
            box-sizing: border-box;
            text-decoration: none;
        }
        .userFio{
            font-weight: bold;
            color: green;
        }
        .confirm{
            color:red;
        }
        .btn_wrapper{
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        button{
            display: flex;
            align-items: center;
            justify-content: center;
            color: #fff!important;
            cursor: pointer;
        }
        .btn_confirm_true{
            background-color: #0f0;
        }
        .btn_confirm_false{
            background-color: #f00;
            margin-left: 10px;
        }
        button a {
            min-width: 100%;
            padding: 8px 4px;
            font-weight: bold;
            font-size: 12px;
            color: #fff!important;
            letter-spacing: 1.1px;
        }
    </style>
</head>
<body>
    <h3>Hello, <span class="userFio">${user.fullName}</span></h3>
    <h3>Your login -> <span class="userFio">${user.login}</span></h3>
    <h3>Your password -> <span class="userFio">${user.password}</span></h3>
    <div>You are registering at Clothes Shop! Are you really a registrant? Please <span class="confirm">confirm</span></div>
    <div class="btn_wrapper">
        <button class="btn_confirm_true"><a href="http://localhost:3000/login">Login</a></button>
    </div>
</body>
</html>