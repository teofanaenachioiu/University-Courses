function  g = mysmallestdenorm()
    last_valid_power = 0;
    power = 0;
    while 2^power > 0
        last_valid_power = power;
        power = power - 1;
    end
    g = 2^last_valid_power;