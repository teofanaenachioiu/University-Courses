function [U]=Benn(p)
    x=rand();
    if x<p
        U=0;
    else
        U=1;
    end
end