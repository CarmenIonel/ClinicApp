package Application.Model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Entity
@Table(name="patient")
public class Pacient
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String cnp;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Date birth;

    public Pacient()
    {

    }

    public Pacient(String name, String cardNumber, String cnp, String address, Date birth)
    {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cnp = cnp;
        this.address = address;
        this.birth = birth;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getCnp()
    {
        return cnp;
    }

    public void setCnp(String cnp)
    {
        this.cnp = cnp;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getBirth()
    {
        return birth;
    }

    public String getDate()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(birth);
    }
    public void setBirth(Date birth)
    {
        this.birth = birth;
    }
}
