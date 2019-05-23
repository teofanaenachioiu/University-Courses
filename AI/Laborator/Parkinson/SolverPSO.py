from __future__ import division
import random
import numpy as np


class Particle:
    def __init__(self,x0):
        self.position_i = []  # particle position
        self.velocity_i = []  # particle velocity
        self.pos_best_i = []  # best position individual
        self.err_best_i = -1  # best error individual/best value function
        self.err_i = -1  # error individual/the function value at some point
        for i in range(0, num_dimensions):
            self.velocity_i.append(random.uniform(-1, 1)) #set velocity for each
            self.position_i.append(x0[i])#set position for individual from the x0 list
    def evaluateFitness(self,our_function):
        l=[]
        li=[]
        for j in range(8):
            li.append(self.position_i[j])
        l.append(li)
        l=np.array(li)
        self.err_i = our_function(l)
        # check to see if the current position is an individual best
        if self.err_i < self.err_best_i or self.err_best_i == -1: #if the value of the function for that point is smaller than the individual best/ind best not set
            self.pos_best_i = self.position_i #update
            self.err_best_i = self.err_i
        return self.err_i
    def update_velocity(self,pos_best_global): #basically we need the global best of all particles, we dont have this in our class
        w=0.5 #constant inertia weight
        c1=1  #cognitive constant
        c2=2  #social constant
        #we set the constants above
        for i in range(0, num_dimensions):
            r1=np.random.random_sample()
            r2=np.random.random_sample()
            #we follow the formula
            vel_cognitive = c1 * r1 * (self.pos_best_i[i] - self.position_i[i])
            vel_social = c2 * r2 * (pos_best_global[i] - self.position_i[i])
            self.velocity_i[i] = w * self.velocity_i[i] + vel_cognitive + vel_social

    # update the particle position based off new velocity updates
    def update_position(self, bounds):
        for i in range(0, num_dimensions):
            self.position_i[i] = self.position_i[i] + self.velocity_i[i]
            # adjust maximum position if necessary
            if self.position_i[i] > bounds[i][1]:
                self.position_i[i] = bounds[i][1]

            # adjust minimum position if neseccary
            if self.position_i[i] < bounds[i][0]:
                self.position_i[i] = bounds[i][0]

class PSO:
    def __init__(self,res, costFunc, x0, bounds, num_particles, maxiter):
        global num_dimensions  # are we in 2D, 3D...?
        num_dimensions = len(x0)
        #here is where we initialize the group needed parameters
        err_best_g = -1  # best error for group
        pos_best_g = []  # best position for group

        # establish the swarm
        swarm = []
        for i in range(0, num_particles):
            swarm.append(Particle(x0)) #at first we create num_particles in the same position, random different velocities tho
        # begin optimization loop
        i = 0
        while i < maxiter:
            list_particlesPos=[]
            for k in range(0,num_particles):
                list_particlesPos.append(swarm[k].position_i)
            #print(list_particlesPos)
            for j in range(0, num_particles): # cycle through particles in swarm and evaluate fitness
                swarm[j].evaluateFitness(costFunc) #for each particle

                # determine if current particle is the best (globally)
                if swarm[j].err_i < err_best_g or err_best_g == -1:
                    pos_best_g = list(swarm[j].position_i)
                    err_best_g = float(swarm[j].err_i)
            #we might print err_best_g to see what happens

            # cycle through swarm and update velocities and position
            for j in range(0, num_particles): # we go next to the next Gen of position, next Gen particles
                swarm[j].update_velocity(pos_best_g)
                swarm[j].update_position(bounds)
            i=i+1
        # print final results
        #print('FINAL:')
        #print(pos_best_g)
        res.append(pos_best_g)

class Method3:
    def __init__(self,dataIn,dataOut):
        self.dataIn=dataIn
        self.dataOut=dataOut
        self.In=self.constructInputMatrix()
        self.Out=self.constructOutMatrix()
    #input matrix from files
    def constructInputMatrix(self): #data initialy given as bidimensional list
        input=self.dataIn
        #standardization
        #for i in range(len(input)):
        #    for j in range(len(input[i])):
        #        input[i][j]=input[i][j]/1000
        for i in range(len(input)):#our vectors are in the form of (1,x) (1 for the free scalar coeff)
            input[i] = [1] + input[i]
        Matrix=np.array(input)
        return Matrix
    def constructOutMatrix(self):
        out=self.dataOut
        Matrix=np.array(out)
        return Matrix
    def constructCoeffMatrix(self): #we have 7 features for each data row so we have to initialize 7+1(free) coefficient which will later be learnt
        l=np.random.rand(1,8)
        l=np.array(l)
        return l
    def cost_function(self,coef): #coef=1 by 8 matrix
        outM=self.Out
        inM=self.In
        n=outM.size
        sum=0
        for i in range(n):
            sum=sum+(outM[i][0]-coef.dot(inM[i].T))**2
        return sum
    def run_method3(self):
        coef=self.constructCoeffMatrix()
        c=[]
        for el in coef[0]:
            c.append(el)
        bounds=[]
        for k in range(8):
            bounds.append((-10000000000000, 100000000000000))
        res=[]
        PSO(res,self.cost_function, c, bounds, num_particles=50, maxiter=499)
        return res
    def model(self,input):
        input[0]=[1]+input[0]
        input=np.array(input)
        l=[]
        r=self.run_method3()
        for el in r[0]:
            l.append([el])
        l=np.array(l)
        res = input.dot(l)
        return res