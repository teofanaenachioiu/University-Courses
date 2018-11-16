function [U]=Bern(p)
    x=rand();
    if x>p
        U=0;
    else
        U=1;
    end
end