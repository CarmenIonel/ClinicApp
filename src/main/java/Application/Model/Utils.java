package Application.Model;

import Application.Repository.ConsultationRepository;
import Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ionel Carmen on 5/5/2017.
 */
public class Utils
{
    //verificare ore
    public boolean verifHour(Date d1, Date d2)
    {
        long h1=d1.getTime();
        long h2=d2.getTime();
        if(h1<h2)
            return true;
        else
            return false;
    }

    //verificare ore
    public int verif(String d1, String d2)
    {
        int ore1=0, min1=0, ore2, min2;
        ore1=d1.charAt(0)*10+d1.charAt(1);
        min1=d1.charAt(3)*10+d1.charAt(4);
        ore2=d2.charAt(0)*10+d2.charAt(1);
        min2=d2.charAt(3)*10+d2.charAt(4);
        if(ore1==ore2 && min1==min2)
            return 0;
        if(ore1<ore2 || (ore1==ore2 && min1<=min2))
            return -1;
        if(ore1>ore2 || (ore1==ore2 && min1>=min2))
            return 1;
        return -2;
    }

    //verificare date
    public int date(String d1, String d2)
    {
        int a1,b1,c1,a2,b2,c2;
        a1=d1.charAt(0)*1000+d1.charAt(1)*100+d1.charAt(2)*10+d1.charAt(3);
        b1=d1.charAt(5)*10+d1.charAt(6);
        c1=d1.charAt(8)*10+d1.charAt(9);

        a2=d2.charAt(0)*1000+d2.charAt(1)*100+d2.charAt(2)*10+d2.charAt(3);
        b2=d2.charAt(5)*10+d2.charAt(6);
        c2=d2.charAt(8)*10+d2.charAt(9);

        if(a1==a2 && b1==b2 &&c1==c2)
        {
            return 0;
        }
        if(a1<a2 || (a1==a2 && b1<b2) || (a1==a2 && b1==b2 && c1<c2))
        {
            return -1;
        }
        return 1;
    }
}
