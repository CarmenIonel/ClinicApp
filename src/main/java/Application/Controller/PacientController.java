package Application.Controller;

import Application.Model.Pacient;
import Application.Model.User;
import Application.Repository.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Controller
public class PacientController
{
    @Autowired
    private PacientRepository pr;

    //add Patient
    @RequestMapping(value="/addPatient", method= RequestMethod.GET)
    public String c(HttpServletRequest ht)
    {
        return "/addPatient";
    }
    @RequestMapping(path="/addPatient", method= RequestMethod.POST)
    public String addP(HttpServletRequest ht)throws Exception
    {
        try
        {
            Pacient p = null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));

            p = new Pacient(ht.getParameter("name"), ht.getParameter("card"), ht.getParameter("cnp"), ht.getParameter("address"), d1);
            pr.save(p);
            return "/secretaryMeniu";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //update
    @RequestMapping(value="/updatePatient", method= RequestMethod.GET)
    public String u(HttpServletRequest ht)
    {
        return "/updatePatient";
    }
    @RequestMapping(path="/updatePatient", method= RequestMethod.POST)
    public String updateP(HttpServletRequest ht) throws Exception
    {
        try
        {
            DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));

            Pacient p = pr.findByName(ht.getParameter("name"));
            if (p == null)
                p = pr.findByCnp(ht.getParameter("cnp"));
            if (ht.getParameter("name1") != "")
                p.setName(ht.getParameter("name1"));
            if (ht.getParameter("card1") != "")
                p.setCardNumber(ht.getParameter("card1"));
            if (ht.getParameter("cnp1") != "")
                p.setCnp(ht.getParameter("cnp1"));
            if (ht.getParameter("date") != "")
                p.setBirth(d1);
            if (ht.getParameter("address") != "")
                p.setAddress(ht.getParameter("address"));
            pr.save(p);
            return "/secretaryMeniu";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //view
    @RequestMapping(path="/{cnp}/viewPatient", method= RequestMethod.GET)
    public String q(@PathVariable String cnp, Model m)
    {
        Pacient p=pr.findByCnp(cnp);
        m.addAttribute("patient", p);
        return "/viewPatient";
    }

    @RequestMapping(value="/viewPatient", method= RequestMethod.GET)
    public String qq(HttpServletRequest ht)
    {
        return "/viewPatient";
    }

    @RequestMapping(value = "/viewPatient", method= RequestMethod.POST)
    public String qqq(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        try
        {
            Pacient p = null;
            p = pr.findByName(request.getParameter("name"));
            if (p == null)
                p = pr.findByCnp(request.getParameter("cnp"));
            return "redirect:/" + p.getCnp() + "/viewPatient";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }
}
