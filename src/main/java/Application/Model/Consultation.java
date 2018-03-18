package Application.Model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Entity
@Table(name="consultation")
public class Consultation
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDoctor")
    private User d;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPacient")
    private Pacient pacient;

    @Column(nullable = false)
    private Date oraI;
    @Column(nullable = false)
    private Date oraS;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String comments;

    public Consultation()
    {

    }

    public Consultation(User d, Pacient p, Date date,Date oraI, Date oraS)
    {
        this.d = d;
        this.pacient = p;
        this.oraI=oraI;
        this.oraS=oraS;
        this.comments="";
        this.date=date;
    }

    public int getId()
    {
        return id;
    }

    public User getDoctor()
    {
        return d;
    }

    public String getNamed()
    {
        return d.getName();
    }

    public void setDoctor(User d)
    {
        this.d = d;
    }

    public Pacient getPacient()
    {
        return pacient;
    }

    public String getNamep()
    {
        return pacient.getName();
    }

    public void setPacient(Pacient p)
    {
        this.pacient = p;
    }

    public Date getOraI()
    {
        return oraI;
    }

    public String getBegin()
    {
        DateFormat dfo = new SimpleDateFormat("HH:mm");
        return dfo.format(oraI);
    }

    public void setOraI(Date oraI)
    {
        this.oraI = oraI;
    }

    public Date getOraS()
    {
        return oraS;
    }

    public String getEnd()
    {
        DateFormat dfo = new SimpleDateFormat("HH:mm");
        return dfo.format(oraS);
    }
    public void setOraS(Date oraS)
    {
        this.oraS = oraS;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments=this.comments+ comments+" ";
    }

    public Date getDate()
    {
        return date;
    }

    public String getData()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
