<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 18/07/2023
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/websiteHomePage1.css">
    <link rel="stylesheet" href="./assets/css/bootstrap.css">
    <%--    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins&display=swap"/>--%>
    <!-- <link media="water-bottle-img"> -->
    <title>Inexture Solutions: Web, Java, and Python Development Company in USA and India</title>
</head>

<body>
<main>
    <div class="box1" id="home">
        <header>
            <nav>
                <ul>
                    <li class="denom">Inexture Solutions</li>
                </ul>
                <ul>
                    <li><a href="#home">Home</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#facilities">Facilities</a></li>
                    <li><a href="#reviews">Reviews</a></li>
                    <li><a href="#news">News</a></li>
                    <li><a href="#contactus">Contact Us</a></li>
                    <li><a href="login.jsp">Login/Sign-Up</a></li>
                </ul>
            </nav>
        </header>
        <div class="content1">
            <div class="title">
                <h1>Innovative. Customized.<br>
                    Scalable.
                </h1>
            </div>
            <p>Amalgamation of design and technology <br><br>
               to produce unparalleled digital experiences.
            </p>
            <button class="contact">CONTACT US</button>
            <button class="read">READ MORE</button>
        </div>
    </div>

    <!-- <div class="about"> -->
    <div class="content2" id="about">
        <div class="images">
            <img class="img1" src="./assets/images/img-1.png" alt="">
            <img class="img2" src="./assets/images/img-2.png" alt="">
            <img class="img3" src="./assets/images/img-3.png" alt="">
        </div>
        <div class="text">
            <h2>ABOUT</h2>
            <p>At Inexture we are constantly pushing the boundaries and thinking
                outside the box to create the next generation of products and services.
            </p>
            <br>
            Our Models:<br>Fixed Price Model<br>On-Demand Model<br>Hire Dedicated Model
            </p>
            <button class="read">READ MORE</button>
        </div>
    </div>
    <!-- </div> -->

    <!-- facility of our marriage house starts here  -->
    <div class="content3" id="facilities">
        <div class="images tennisimg">
            <img src="./assets/images/tennis-img.png">
        </div>
        <div class="content3-heading">
            <h2>FACILITIES OF OUR PREMISES</h2>
            <p>Our offices are located at one of the most prime areas of the city,
                <br>furbished with cutting edge technology, and belly full laughs.
            </p>
        </div>
        <div class="innercontainer3">
            <div class="car">
                <img src="./assets/images/car-img.png" alt="Car"/>
                <div>
                    <h4>Car Parking</h4>
                    <p>
                        Dual level underground parking for your vehicles,
                        <br/>
                        hydraulic car lifts available to accommodate more of your cars.
                    </p>
                </div>
            </div>

            <div class="ball">
                <img src="./assets/images/balls-img.png" alt="balls"/>
                <div class="balltext">
                    <h4>Celebrations</h4>
                    <p>
                        Festivals are celebrated in full spirits at the office,
                        <br/>
                        helping you connect better with your family (in addition to the biological one).
                    </p>
                </div>
            </div>

            <div class="bottle">
                <img src="./assets/images/water-bottle-img.png" alt="bottle"/>
                <div>
                    <h4>Pantry and Food</h4>
                    <p>
                        The office premises offers a pantry space for having meals,
                        <br/>
                        cooking food, water, and refreshments.
                    </p>
                </div>
            </div>
        </div>
    </div>


    <!-- facility of marriage house ends here  -->


    <!-- blog starts here -->

    <div class="content4">
        <div id="reviews">
            <h2 class="content4-heading">Reviews</h2>
        </div>
        <div>
            <p>Their words, not ours.</p>
        </div>
        <div class="blog-articles">
            <div class="feb6">
                <div class="feb6-image"><img src="./assets/images/img-4.jpg"></div>
                <div class="feb6-text">
                    <h2>Roger Paul, USA</h2>
                    Under this model, clients pay for actual development hrs, which offers flexibility as they
                    can request new features or changes as the project progresses. This approach fosters trust
                    and open communication between the developer and the client.
                    <div class="feb6-links">
                        <div class="like">
                            <a href="#">Helpful</a>
                        </div>
                        <div class="comment">
                            <a href="#">Comment</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="feb2">

            <div class="feb6-text">
                <h2>Emily Chargin, USA</h2>
                Working with Inexture was a great experience. They listened to our needs,
                were responsive throughout the process, and delivered a high-quality product
                that met our expectations. We highly recommend them.
                <div class="feb6-links">
                    <div class="like">
                        <a href="#">Helpful</a>
                    </div>
                    <div class="comment">
                        <a href="#">Comment</a>
                    </div>
                </div>
            </div>
            <div class="feb6-image"><img src="./assets/images/img-4.jpg"></div>
        </div>
    </div>
    <!-- blog ends here  -->

    <!-- contact section start -->
    <div class="contact_srction layout_padding" id="contactus">
        <div class="container">
            <h1 class="contact_title">Request a call back</h1>
            <div class="contact_section_2">
                <div class="mail_main">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" class="mail_text" placeholder="Full Name" name="Full Name">
                            <input type="text" class="mail_text" placeholder="Phone Number" name="Phone Number">
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="mail_text" placeholder="Email" name="Email">
                            <textarea class="massage-bt" placeholder="Message" rows="20" id="comment"
                                      name="Massage"></textarea>
                        </div>
                    </div>
                    <div class="send_bt">
                        <a href="#">SEND</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- contact section end -->

    <!-- news section start -->
    <div class="review_section layout_padding" id="news">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-8">
                    <div class="review_main">
                        <h1 class="review_title">What our Directors Say:</h1>
                        <div class="review_section_2">
                            <h3 class="lina_text">Vishal Shah, Dharmesh Patel,<br> and Mahipalsinh Rana</h3>
                            <p class="dummy_text">Inexture is a decision you won't regret. We think outside the code.
                                From conceptualization to deployment, we pledge to bestow inventive and dependable
                                software solutions that empower you to maintain an edge in the competitive landscape.
                            </p>
                            <div class="quick_icon directors_img"><img src="./assets/images/quick-icon.png"></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 padding_right_0">
                    <div class="review_img"><img src="./assets/images/news-img.png"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- news section end -->


    <!-- footer section start -->
    <div class="footer_section">
        <div class="container">
            <div class="social_icon">
                <a class="padding_10" href="#"><img src="./assets/images/fb-icon.png"></a>
                <a class="padding_10" href="#"><img src="./assets/images/twitter-icon.png"></a>
                <a class="padding_10" href="#"><img src="./assets/images/linkedin-icon.png"></a>
                <a class="padding_10" href="#"><img src="./assets/images/youtub-icon.png"></a>
            </div>
            <div class="location_main">
                <div class="location_text"><img src="./assets/images/map-icon.png"><span class="padding10">
                            <a href="#">GB Road 123 London UK</a>
                        </span>
                </div>
                <div class="location_text center"><img src="./assets/images/call-icon.png"><span class="padding10">
                            <a href="#">Call : (+01) 123456789012</a>
                        </span>
                </div>
                <div class="location_text right"><img src="./assets/images/mail-icon.png"><span class="padding10">
                            <a href="#">inexturesolutions@inexture.com</a>
                        </span>
                </div>
            </div>
            <div class="Enter_mail_main">
                <input type="text" class="Enter_text" placeholder="Enter Your Name" name="Enter Your Name">
                <div class="subscribe_bt">
                    <a href="#">Subscribe</a>
                </div>
            </div>
        </div>
    </div>

    <div class="copyright_section">
        <div class="container">
            <p class="copyright_text">Copyright 2023 All Rights Reserved by Inexture Solutions.<a href="#"></a>
            </p>
        </div>
    </div>
    <!-- footer section end -->
</main>

</body>

</html>
<jsp:include page="./WEB-INF/views/footer.jsp"/>
