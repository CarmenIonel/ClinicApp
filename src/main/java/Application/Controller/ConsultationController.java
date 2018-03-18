package Application.Controller;

import Application.Model.Consultation;
import Application.Model.Pacient;
import Application.Model.User;
import Application.Model.Utils;
import Application.Repository.ConsultationRepository;
import Application.Repository.PacientRepository;
import Application.Repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Controller
public class ConsultationController
{
    @Autowired
    private ConsultationRepository cr;

    @Autowired
    private PacientRepository pr;

    @Autowired
    private UserRepository ur;

    Utils utils=new Utils();

    //SECRETARY

    //consultatiile unui doctor
    public List<Consultation> getCons(User doctor)
    {
        List<Consultation> c=cr.findAll();
        for (int i=0; i<c.size(); i++)
            if(!(c.get(i). getDoctor()==doctor))
                c.remove(c.get(i));
        return c;
    }

    public boolean verifOrar(User doctor, Date d1, Date d2, Date date)
    {

        List<Consultation> c=getCons(doctor);
        for(int i=0; i<c.size(); i++)
        {

            DateFormat dfo = new SimpleDateFormat("HH:mm");

            String o1=dfo.format(c.get(i).getOraI());//incepe cons
            String o2=dfo.format(c.get(i).getOraS());//gata cons
            String d11=dfo.format(d1); //start cons
            String d22=dfo.format(d2);//end cons

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String x1=df.format(c.get(i).getDate());
            String x2=df.format(date);
            if(utils.date(x1,x2)==0)
            {
                if (!((utils.verif(d11, o1) == -1 && utils.verif(d22, o1) == -1) ||
                        (utils.verif(d11, o2) == 1 && utils.verif(d22, o2) == 1) ))
                    return false;
            }
        }
        return true;
    }
    public List<User> getDoctor(Date d1, Date d2, Date date)
    {
        List<User> list=new ArrayList<User>();
        List<User> l=ur.findAll();
        //doar doctorii
        for(int i=0; i<l.size(); i++)
            if(!(l.get(i).getRole().equalsIgnoreCase("ROLE_DOCTOR")))
                l.remove(l.get(i));
        //doar doctorii care au program

        for(int i=0; i<l.size(); i++)
        {
            DateFormat dfo = new SimpleDateFormat("HH:mm");
            String o1=dfo.format(l.get(i).getDateI());
            String o2=dfo.format(l.get(i).getDateS());
            String d11=dfo.format(d1);
            String d22=dfo.format(d2);
            if(utils.verif(o1,d11)==1 || utils.verif(o2,d22)==-1)
                l.remove(l.get(i));
        }

        //doctorii care sunt diponibili
        for(int i=0; i<l.size(); i++)
            if(verifOrar(l.get(i), d1, d2,date))
                list.add(l.get(i));
        System.out.println(list.size());
        return list;
    }

    //add Consultation
    @RequestMapping(value="/addConsultation", method= RequestMethod.GET)
    public String c(HttpServletRequest ht)
    {
        return "/addConsultation";
    }
    @RequestMapping(path="/addConsultation", method= RequestMethod.POST)
    public String addC(HttpServletRequest ht)throws Exception
    {
        try
        {
            Pacient p = null;
            User d = null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));
            String d11=df.format(d1);


            Date cur=new Date();
            String cur1=df.format(cur);

            DateFormat dfo = new SimpleDateFormat("HH:mm");
            Date ora1=dfo.parse(ht.getParameter("oraI"));
            Date ora2=dfo.parse(ht.getParameter("oraS"));
            String ora11=dfo.format(ora1);
            String ora22=dfo.format(ora2);


            if(utils.verif(ora11,ora22)==-1 && (utils.date(cur1,d11)==-1 || utils.date(cur1,d11)==0))
            {
                p = pr.findByName(ht.getParameter("namep"));
                if (p == null)
                    return "/addPatient";

                List<User> doctors = getDoctor(ora1, ora2, d1);
                d = doctors.get(0);
                Consultation c = new Consultation(d, p, d1, ora1, ora2);
                c.setComments(ht.getParameter("comment"));

                cr.save(c);
                return "/secretaryMeniu";
            }
            else
                return "/error";

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "/error";
        }
    }

    //update Consultation
    @RequestMapping(value="/updateConsultation", method= RequestMethod.GET)
    public String u(HttpServletRequest ht)
    {
        return "/updateConsultation";
    }
    @RequestMapping(path="/updateConsultation", method= RequestMethod.POST)
    public String updateConsultation(HttpServletRequest ht) throws Exception
    {
        try
        {
            Consultation c=null;
            Consultation c1=null;
            Pacient p=null;
            User d=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));
            String d11=df.format(d1);

            Date cur=new Date();
            String cur1=df.format(cur);

            DateFormat dfo = new SimpleDateFormat("HH:mm");
            Date ora=dfo.parse(ht.getParameter("oraI"));

            p = pr.findByName(ht.getParameter("name"));
            if(p==null)
                return "/addPatient";
            else {
                c = cr.findByPacientAndDateAndOraI(p, d1, ora);
                if (c == null)
                    return "/addConsultation";
                else
                {
                    if (ht.getParameter("oraI1") != "")
                    {
                        Date ora1=dfo.parse(ht.getParameter("oraI1"));
                        Date ora2=dfo.parse(dfo.format(c.getOraI()));
                        Date ora3=dfo.parse(dfo.format(c.getOraS()));

                        String ora11=dfo.format(ora1);
                        String ora22=dfo.format(ora2);
                        String ora33=dfo.format(ora3);

                        if(utils.verif(ora11,ora33)==-1)
                        {
                            c.setDoctor(null);
                            c.setPacient(null);
                            cr.save(c);
                            cr.delete(c);
                            List<User> doctors = getDoctor(ora1, ora2, d1);
                            d = doctors.get(0);
                            if(d==null)
                                return "/error";
                            else
                            {
                                c.setOraI(ora1);
                                c.setDoctor(d);
                                c.setPacient(p);
                            }
                        }
                        else
                            return "/error";
                    }

                    if (ht.getParameter("oraS") != "")
                    {

                        Date ora1 = dfo.parse(ht.getParameter("oraS"));
                        Date ora2 = dfo.parse(dfo.format(c.getOraI()));
                        Date ora3 = dfo.parse(dfo.format(c.getOraS()));

                        String ora11 = dfo.format(ora1);
                        String ora22 = dfo.format(ora2);

                        if (utils.verif(ora22, ora11) == -1) {
                            c.setDoctor(null);
                            c.setPacient(null);
                            cr.save(c);
                            cr.delete(c);
                            List<User> doctors = getDoctor(ora2, ora1, d1);
                            d = doctors.get(0);
                            if (d == null)
                                return "/error";
                            else {
                                c.setOraS(ora1);
                                c.setDoctor(d);
                                c.setPacient(p);
                            }
                        } else
                            return "/error";
                    }
                    if (ht.getParameter("date1") != "")
                    {

                        Date d10 = df.parse(ht.getParameter("date1"));
                        String d100=df.format(d10);

                        Date ora2 = dfo.parse(dfo.format(c.getOraI()));
                        Date ora3 = dfo.parse(dfo.format(c.getOraS()));

                        if(utils.date(cur1,d100)==-1 || utils.date(cur1,d100)==0)
                        {
                            c.setDoctor(null);
                            c.setPacient(null);
                            cr.save(c);
                            cr.delete(c);
                            List<User> doctors = getDoctor(ora2,ora3, d1);
                            d = doctors.get(0);
                            if(d==null)
                                return "/error";
                            else
                            {
                                c.setDate(d10);
                                c.setDoctor(d);
                                c.setPacient(p);
                            }
                        }
                        else
                            return "/error";
                    }
                    cr.save(c);
                    return "/secretaryMeniu";

                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "/error";
        }
    }

    //delete Consultation
    @RequestMapping(value="/deleteConsultation", method= RequestMethod.GET)
    public String d(HttpServletRequest ht)
    {
        return "/deleteConsultation";
    }
    @RequestMapping(path="/deleteConsultation", method= RequestMethod.POST)
    public String deleteConsultation(HttpServletRequest ht)throws Exception
    {
        try
        {
            Pacient p=null;
            Consultation c=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));
            String d11=df.format(d1);
            System.out.println(d11);

            DateFormat dfo = new SimpleDateFormat("HH:mm");
            Date ora=dfo.parse(ht.getParameter("oraI"));
            System.out.println(ora);

            p = pr.findByName(ht.getParameter("numep"));
            if(p==null)
                return "/addPatient";

            c = cr.findByPacientAndDateAndOraI(p, d1, ora);
            if(c==null)
                return "/addConsultation";
            c.setPacient(null);
            c.setDoctor(null);
            cr.save(c);
            cr.delete(c);
            return "/secretaryMeniu";
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "/error";
        }
    }

    //view Consultation
    @RequestMapping(path="/{id}/viewConsultation", method= RequestMethod.GET)
    public String e(@PathVariable String id, Model m)
    {
        Consultation c=cr.findOne(Integer.parseInt(id));
        m.addAttribute("consultation", c);
        return "/viewConsultation";
    }

    @RequestMapping(value="/viewConsultation", method= RequestMethod.GET)
    public String em(HttpServletRequest ht)
    {
        return "/viewConsultation";
    }

    @RequestMapping(value = "/viewConsultation", method= RequestMethod.POST)
    public String emp(HttpServletRequest ht,HttpServletResponse response) throws Exception
    {
        try
        {
            Pacient p=null;
            Consultation c=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));
            String d11=df.format(d1);
            System.out.println(d11);

            DateFormat dfo = new SimpleDateFormat("HH:mm");
            Date ora=dfo.parse(ht.getParameter("oraI"));
            System.out.println(ora);

            p = pr.findByName(ht.getParameter("name"));
            if(p==null)
                return "/addPatient";

            c = cr.findByPacientAndDateAndOraI(p, d1, ora);
            if(c==null)
                return "/addConsultation";

            return "redirect:/" + c.getId() + "/viewConsultation";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    @RequestMapping(value="/patientArrived", method= RequestMethod.GET)
    public String emsf(HttpServletRequest ht)
    {
        return "/patientArrived";
    }

    @RequestMapping(path="/patientArrived", method= RequestMethod.POST)
    public String arriv(HttpServletRequest ht)throws Exception
    {
       try
       {
           Pacient p = null;
           Consultation c = null;
           User d = null;

           p=pr.findByName(ht.getParameter("name"));

           DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           Date date=new Date();
           Date d1=df.parse(df.format(date));

           if(p==null)
               return "/addPatient";
           else
           {
               c = cr.findByPacientAndDate(p, d1);
               if (c == null)
                   return "/addConsultation";
               else
               {
                   d=ur.findByUsername(c.getDoctor().getUsername());
                   d.setShouldBeNotified(true);
                   System.out.println(d.getName());
                   ur.save(d);
               }
           }
           return "/patientArrived";
       }
       catch(Exception e)
       {
           return "/error";
       }
    }



    //DOCTOR
    //view
    @RequestMapping(path="/{name}/viewComments", method= RequestMethod.GET)
    public String e3(@PathVariable String name, Model m)
    {

        Pacient p = pr.findByName(name);
        List<Consultation> c=cr.findByPacient(p);
        m.addAttribute("consultation", c);
        return "/viewComments";
    }

    @RequestMapping(value="/viewComments", method= RequestMethod.GET)
    public String em3(HttpServletRequest ht)
    {
        return "/viewComments";
    }

    @RequestMapping(value = "/viewComments", method= RequestMethod.POST)
    public String emp3(HttpServletRequest ht,HttpServletResponse response) throws Exception
    {
        try
        {
            Pacient p=null;
            List<Consultation> c;

            p = pr.findByName(ht.getParameter("name"));
            if(p==null)
                return "/addPatient";
            c = cr.findByPacient(p);
            if(c==null)
                return "/addConsultation";

            return "redirect:/" +p.getName()+ "/viewComments";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }
    //add
    @RequestMapping(value="/addComments", method= RequestMethod.GET)
    public String cc(HttpServletRequest ht)
    {
        return "/addComments";
    }
    @RequestMapping(path="/addComments", method= RequestMethod.POST)
    public String addCc(HttpServletRequest ht)throws Exception
    {
        try
        {
            Pacient p=null;
            Consultation c=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(ht.getParameter("date"));
            String d11=df.format(d1);
            System.out.println(d11);

            DateFormat dfo = new SimpleDateFormat("HH:mm");
            Date ora=dfo.parse(ht.getParameter("oraI"));
            System.out.println(ora);

            p = pr.findByName(ht.getParameter("name"));
            if(p==null)
                return "/addPatient";

            c = cr.findByPacientAndDateAndOraI(p, d1, ora);
            if(c==null)
                return "/addConsultation";


            if(ht.getParameter("comm")!="")
            {
                User d=c.getDoctor();
                c.setDoctor(null);
                c.setPacient(null);
                cr.save(c);
                cr.delete(c);
                c.setPacient(p);
                c.setDoctor(d);
                c.setComments(ht.getParameter("comm"));
                cr.save(c);
                return "/doctorMeniu";
            }
            else
                return "/error";

        }
        catch(Exception e)
        {
            return "/error";
        }
    }

}
