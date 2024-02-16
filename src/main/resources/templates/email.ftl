<!DOCTYPE html>
<html lang="en">
<head>
    <title> User Query Form Email Page.</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
    <style> 
        body {
            margin:0px;
            padding:0px;
            border:0px;
            font-family: 'Roboto', sans-serif;
            font-size: 16px;
        }

        a {
            font-size: 13px;
            font-weight: bold;
            color: #000;
        }
        
        address {
            text-align: center;
            font-size: 13px;
            padding-top: 3px;
            font-weight: bold;
            font-style: normal;
        }
        ul {
            display:block;
            max-width: 90%;
            margin:0px auto;
            text-align: center;
        }

        ul li {
            list-style-type: none;
            display: inline;
            padding:2px 5px;
        }

        p {
            font-size: 14px;
        }

        .text-center {
            text-align: center;
        }

        .bold {
            font-weight: bold;
        }

        .bg-light {
            background: #f8f9fa;
        }

        .bg-info {
            background-color: #FFF;
        }

        .container {
            width: 80%;
            height:100%;
            margin: 0px auto;
        }

        .navbar {
            width: 100%;
            padding: 30px 0px;
        }

        .navbar a img {
            display: block;
            max-width: 80%;
            margin-top: 50px;
            margin-bottom: 10px;
            margin-right: auto;
            margin-left: auto;
        }

        .row {
            max-width: 80%;
            margin: 5px auto;
        }
        
        .col-md-10 {
            max-width: 100%;
            max-height: 100%;
            margin: 5px auto;
        }

        .footer {
            max-width: 100%;
            margin: 20px auto;
            margin-bottom: 20px;
        }
        
        .copyright-text {
            text-align: center;
            font-size:12px;
        }

        .contact-info {
            font-size: 13px;
            text-align:inherit;
            margin-top: 3px;
        }

        section section {
            padding:2px 15px;
            text-transform: none;
        }
        
        .main-msg {
            max-width: 90%;
        }

        section footer {
            padding: 5px 15px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="container bg-light">
        <nav class="navbar">
            <a href="http://localhost:8181/">
                <img src="cid:logo.png" style="margin: 0px auto;" alt="logo"/>
            </a>
        </nav>

        <div class="row">
                <div class="row">
                    <h3> Dear ${cname}, </h3>
                </div>
                <div class="row bg-info">
                    <section>
                        <section class="main-msg">
                                <p>Your account have been <b> successfully </b> created. However to be able to use our site you have to <b> activate </b> your account. You can activate your account by clicking the following link. </p>
                                <a href="${activationLink}"> Activate Account </a>
                                <br>
                        </section>
                        
                        <footer>
                            <p>Thanks, </p>
                        </footer>
                    </section>
                </div>
        </div>

        <div class="row">
            <footer class="footer"> 
                <nav class="navbar">
                    <ul>
                        <li><a href="#"> Contact Support</a></li>
                        <li><a href="#"> Privacy Policy </a></li>
                        <li><a href="#"> Terms Of Use </a></li>
                    </ul>
                    <div class="row">
                        <address>${location}
                            <p class="contact-info">Contact No: ${officeContactNo}</p>
                        </address>
                    </div>
                    <div class="row"><p class="copyright-text"> &copy; 2018. iPasal Inc. </p> </div>
                </nav>
            </footer>
        </div>
    </div>
</body>
</html>