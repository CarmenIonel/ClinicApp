package Application.Controller;

import Application.Model.User;
import Application.Model.Utils;
import Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Controller
public class UserController
{
    @Autowired
    private UserRepository ur;

    private Utils ut=new Utils();

    //add User
    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String c(HttpServletRequest ht)
    {
        return "/create";
    }
    @RequestMapping(path="/create", method= RequestMethod.POST)
    public String addUser(HttpServletRequest ht)throws Exception
    {
        try
        {
            User user = null;
            System.out.println("mata");
            DateFormat df = new SimpleDateFormat("HH:MM");
            Date d1 = df.parse(ht.getParameter("oraI"));
            Date d2 = df.parse(ht.getParameter("oraS"));
            user = new User(ht.getParameter("name"), ht.getParameter("address"), ht.getParameter("phone"), ht.getParameter("email"),
                        d1, d2, ht.getParameter("role"), ht.getParameter("username"), ht.getParameter("password"));
            System.out.println(user.toString());
            if(ut.verifHour(d1,d2)==false)
            {
                user=null;
            }
            ur.save(user);
            return "/adminMeniu";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //update
    @RequestMapping(value="/update", method= RequestMethod.GET)
    public String u(HttpServletRequest ht)
    {
        return "/update";
    }
    @RequestMapping(path="/update", method= RequestMethod.POST)
    public String updateUser(HttpServletRequest ht) throws Exception
    {
        try
        {
            User user = null;

            DateFormat df = new SimpleDateFormat("HH:MM");
            Date d1 = df.parse(ht.getParameter("oraI"));
            Date d2 = df.parse(ht.getParameter("oraS"));

            user = ur.findByName(ht.getParameter("name"));
            if (user == null)
                user = ur.findByUsername(ht.getParameter("username"));
            if (ht.getParameter("name1") != "")
                user.setName(ht.getParameter("name1"));
            if (ht.getParameter("address") != "")
                user.setAddress(ht.getParameter("address"));
            if (ht.getParameter("email") != "")
                user.setEmail(ht.getParameter("email"));
            if (ht.getParameter("phone") != "")
                user.setPhone(ht.getParameter("phone"));
            if (ht.getParameter("password") != "")
                user.setPassword(ht.getParameter("password"));
            if (ht.getParameter("role") != "")
                user.setRole(ht.getParameter("role"));
            if (ht.getParameter("oraI") != "")
                user.setStart(d1);
            if (ht.getParameter("oraS") != "")
                user.setEnd(d2);
            if (ht.getParameter("username1") != "")
                user.setUsername(ht.getParameter("username1"));
            if(ut.verifHour(d1,d2)==false)
                user=null;
            ur.save(user);
            return "/adminMeniu";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //view
    @RequestMapping(path="/{name}/view", method= RequestMethod.GET)
    public String e(@PathVariable String name, Model m)
    {
        User u=ur.findByName(name);
        m.addAttribute("user", u);
        return "/view";
    }

    @RequestMapping(value="/view", method= RequestMethod.GET)
    public String em(HttpServletRequest ht)
    {
        return "/view";
    }

    @RequestMapping(value = "/view", method= RequestMethod.POST)
    public String emp(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        try
        {
            User u = null;
            u = ur.findByName(request.getParameter("name"));
            if (u == null)
                u = ur.findByUsername(request.getParameter("username"));
            return "redirect:/" + u.getName() + "/view";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //delete
    @RequestMapping(value="/delete", method= RequestMethod.GET)
    public String d(HttpServletRequest ht)
    {
        return "/delete";
    }
    @RequestMapping(path="/delete", method= RequestMethod.POST)
    public String deleteUser(HttpServletRequest ht)throws Exception
    {
        try
        {
            User user = null;
            user = ur.findByName(ht.getParameter("name"));
            if (user == null)
                user = ur.findByUsername(ht.getParameter("username"));
            if(user.getUsername().equalsIgnoreCase(ht.getRemoteUser()))
                user=null;
            System.out.println(ht.getRemoteUser());
            ur.delete(user);
            return "/adminMeniu";
        }
        catch(Exception e)
        {
            return "/error";
        }
    }

    //polling
    @RequestMapping(value = "/doctorMeniu/poll",method = RequestMethod.POST)
    public @ResponseBody
    Boolean poll(HttpServletRequest request)
    {
        System.out.println("poll");
        try
        {
            User user =null;
            user=ur.findByUsername(request.getRemoteUser());
            if ( user.getShouldBeNotified()==true)
            {
                user.setShouldBeNotified(false);
                ur.save(user);
                return true;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
