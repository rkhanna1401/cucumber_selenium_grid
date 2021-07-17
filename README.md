# cucumber_selenium_grid

Use below configuration options for execution:

Using Junit +  Local System :

     Set grid_mode=OFF and docker=false in configuration.properties file
     Go to TestRunner class > Run As > Junit
     Reports: For Report Generation and Validation > Go to Target folder > Open 
                      terminal > Type allure generate —clean

using TestNG + Local System:

    Set grid_mode=OFF and docker=false in configuration.properties file
    Run testNG tests either through testng.xml or through Test Runner Class or through Maven using terminal command mvn clean test
    Reports: TestNG reports can be seen under target > cucumber > overview-
                   feature.html

Using TestNG + Grid on local system:

     Set grid_mode=ON and docker=false in configuration.properties file
     Run testNG tests either through testng.xml or through Test Runner Class or through Maven using terminal command mvn clean test
     Reports: TestNG reports can be seen under target > cucumber > overview-
                    feature.html
                    
            
Using TestNG + Grid Execution on Docker Environment:

     Set grid_mode=ON and docker=true in configuration.properties file
     Start docker container using docker-compose up -d command
     Reports: TestNG reports can be seen under target > cucumber > overview-
                   feature.html





