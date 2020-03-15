function  g = myrealmin()
    power = 0;

    while 2^power > 0
        power = power - 1;
    end

    power = power+53;
    g = 2^power;
