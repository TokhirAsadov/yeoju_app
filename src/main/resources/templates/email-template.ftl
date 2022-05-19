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
        h3{
            font-weight: 300;
            font-size: 28px;
        }
        .userFio{
            font-weight: bold;
            color: #0096DB;
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
            border: none;
        }
        .btn_confirm_true{
            background-color: #0096DB;
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
    <div class="btn_wrapper">
        <button class="btn_confirm_true"><a href="http://localhost:3000/login">Login</a></button>
    </div>
</body>
</html>