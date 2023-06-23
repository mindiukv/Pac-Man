import acm.graphics.GImage;

import java.util.Random;

public class MoveGhost {

    private boolean flag_left;
    private boolean flag_right;
    private boolean flag_down;
    private boolean flag_up;
    private GImage ghost;
    private int speed = 7;
    private boolean flag_left1;
    private boolean flag_right1;
    private boolean flag_down1;
    private boolean flag_up1;
    MoveGhost(GImage ghost, boolean flag_down, boolean flag_left, boolean flag_up, boolean flag_right)
    {
        this.ghost=ghost;
        this.flag_down=flag_down;
        this.flag_left=flag_left;
        this.flag_right=flag_right;
        this.flag_up=flag_up;
    }
    MoveGhost(GImage ghost, boolean flag_down, boolean flag_left, boolean flag_up, boolean flag_right, int speed)
    {
        this.ghost=ghost;
        this.flag_down=flag_down;
        this.flag_left=flag_left;
        this.flag_right=flag_right;
        this.flag_up=flag_up;
        this.speed = speed;
    }

    public  int moveX()
    {
        if(flag_left1)
        {
            flag_left1=false;
            return speed;
        }
        if(flag_right1)
        {
            flag_right1=false;
            return -speed;
        }
        if(flag_right)
        {
            return speed;
        }
        if(flag_left){
            return -speed;
        }
        else return 0;

    }
    public int moveY()
    {
        if(flag_up1)
        {
            flag_up1=false;
            return speed;
        }
        if(flag_down1)
        {
            flag_down1=false;
            return -speed;
        }
        if(flag_up)
        {
            return -speed;
        }
        if(flag_down)
        {
            return speed;
        }
        else return 0;
    }
    public GImage getGhost()
    {
        return ghost;
    }
    public void setRight()
    {
        flag_left=false;
        flag_right=true;
        flag_up=false;
        flag_down=false;
    }
    public void setLeft()
    {
        flag_left=true;
        flag_right=false;
        flag_up=false;
        flag_down=false;
    }
    public void setDown(){
        flag_down=true;
        flag_up=false;
        flag_right=false;
        flag_left=false;
    }
    public void setUp()
    {
        flag_down=false;
        flag_up=true;
        flag_right=false;
        flag_left=false;
    }
    public void set() {
        Random random = new Random();

        int number = random.nextInt(3) + 1;
        if(flag_down)
        {
            flag_down=false;
            flag_down1=true;
            if(number==1)
            {
                flag_up=true;
                flag_left=false;
                flag_right=false;
            }
            else if(number==2)
            {
                flag_right=true;
                flag_up=false;
                flag_left=false;
            }
            else {
                flag_left=true;
                flag_up=false;
                flag_right=false;
            }

        }
        else if(flag_up){
            flag_up=false;
            flag_up1=true;
            if(number==1)
            {
                flag_down=true;
                flag_right=false;
                flag_left=false;
            }
            else if(number==2)
            {
                flag_right=true;
                flag_left=false;
                flag_down=false;
            }
            else {
                flag_left=true;
                flag_right=false;
                flag_down=false;
            }
        }
        else if(flag_left)
        {
            flag_left=false;
            flag_left1=true;
            if(number==1)
            {
                flag_down=true;
                flag_right=false;
                flag_up=false;
            }
            else if(number==2)
            {
                flag_right=true;
                flag_up=false;
                flag_down=false;
            }
            else {
                flag_up=true;
                flag_right=false;
                flag_down=false;
            }
        }
        else {
            flag_right=false;
            flag_right1=true;
            if(number==1)
            {
                flag_down=true;
                flag_up=false;
                flag_left=false;
            }
            else if(number==2)
            {
                flag_up=true;
                flag_left=false;
                flag_down=false;
            }
            else {
                flag_left=true;
                flag_up=false;
                flag_down=false;
            }
        }
    }
    public int getSpeed()
    {
        return speed;
    }
}
