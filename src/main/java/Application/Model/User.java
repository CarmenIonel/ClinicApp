package Application.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Entity
@Table(name="user")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date start;
    @Column(nullable = false)
    private Date end;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean shouldBeNotified;

    public User()
    {

    }

    public User(String role, String username, String password)
    {
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public User(String name, String address, String phone, String email, Date start, Date end, String role, String username, String password)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.start=start;
        this.end=end;
        this.role = "ROLE_"+role.toUpperCase();
        this.username = username;
        this.password = password;
        this.shouldBeNotified=false;
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStart()
    {
        DateFormat df = new SimpleDateFormat("HH:MM");
        return df.format(start);
    }

    public Date getDateI()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start=start;
    }

    public String getEnd()
    {
        DateFormat df = new SimpleDateFormat("HH:MM");
        return df.format(end);
    }

    public Date getDateS()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end=end;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isAccountNonExpired()
    {
        return true;
    }

    public boolean isAccountNonLocked()
    {
        return true;
    }

    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    public boolean isEnabled()
    {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return AuthorityUtils.createAuthorityList(this.getRole());
    }

    public boolean getShouldBeNotified()
    {
        return shouldBeNotified;
    }

    public void setShouldBeNotified(boolean shouldBeNotified)
    {
        this.shouldBeNotified = shouldBeNotified;
    }
}
