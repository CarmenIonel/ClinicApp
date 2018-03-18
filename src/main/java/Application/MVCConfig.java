package Application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter
{
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    public void addViewControllers(ViewControllerRegistry reg)
    {
        //login
        reg.addViewController("/login").setViewName("login");
        reg.addViewController("/").setViewName("login");

        //pass
        reg.addViewController("/pass").setViewName("pass");
        reg.addViewController("/denied").setViewName("denied");

        //error
        reg.addViewController("/error").setViewName("error");

        //meniu-done
        reg.addViewController("/adminMeniu").setViewName("/adminMeniu");
        reg.addViewController("/doctorMeniu").setViewName("/doctorMeniu");
        reg.addViewController("/secretaryMeniu").setViewName("/secretaryMeniu");

        //user-done
        reg.addViewController("/create").setViewName("/create");
        reg.addViewController("/update").setViewName("/update");
        reg.addViewController("/delete").setViewName("/delete");
        reg.addViewController("/view").setViewName("/view");

        //patient-done
        reg.addViewController("/addPatient").setViewName("/addPatient");
        reg.addViewController("/updatePatient").setViewName("/updatePatient");
        reg.addViewController("/viewPatient").setViewName("/viewPatient");

        //consultation-done
        reg.addViewController("/deleteConsultation").setViewName("/deleteConsultation");
        reg.addViewController("/addConsultation").setViewName("/addConsultation");
        reg.addViewController("/viewConsultation").setViewName("/viewConsultation");
        reg.addViewController("/updateConsultation").setViewName("/updateConsultation");
        reg.addViewController("/patientArrived").setViewName("/patientArrived");

        //
        reg.addViewController("/viewComments").setViewName("/viewComments");
        reg.addViewController("/addComments").setViewName("/addComments");
    }
}