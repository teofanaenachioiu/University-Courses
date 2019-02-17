/**
 * Created by grigo on 5/18/17.
 */
import React from  'react';
import UserTable from './User';
import './UserApp.css'
import UserForm from "./UserForm";
import {GetUsers, DeleteUser, AddUser} from './utils/rest-calls'


class UserApp extends React.Component{
    constructor(props){
        super(props);
        this.state={users:[{"passwd":"maria","name":"Marinescu Maria","id":"maria"}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
        }
        console.log('UserApp constructor')
    }

    addFunc(user){
        console.log('inside add Func '+user);
        AddUser(user)
            .then(res=> GetUsers())
            .then(users=>this.setState({users}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }


    deleteFunc(user){
        console.log('inside deleteFunc '+user);
        DeleteUser(user)
            .then(res=> GetUsers())
            .then(users=>this.setState({users}))
            .catch(error=>console.log('eroare delete', error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetUsers().then(users=>this.setState({users}));
    }

    render(){
        return(
            <div className="UserApp">
                <h1>Chat User Management</h1>
                <UserForm addFunc={this.state.addFunc}/>
                <br/>
                <br/>
                 <UserTable users={this.state.users} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default UserApp;