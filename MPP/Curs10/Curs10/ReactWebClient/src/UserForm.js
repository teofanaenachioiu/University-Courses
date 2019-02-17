/**
 * Created by grigo on 5/19/17.
 */
import React from  'react';
class UserForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {username: '', name:'', passwd:''};

      //  this.handleChange = this.handleChange.bind(this);
       // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUserChange=(event) =>{
        this.setState({username: event.target.value});
    }

    handleNameChange=(event) =>{
        this.setState({name: event.target.value});
    }

    handlePasswdChange=(event) =>{
        this.setState({passwd: event.target.value});
    }
    handleSubmit =(event) =>{

        var user={id:this.state.username,
                name:this.state.name,
                passwd:this.state.passwd
        }
        console.log('A user was submitted: ');
        console.log(user);
        this.props.addFunc(user);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username:
                    <input type="text" value={this.state.username} onChange={this.handleUserChange} />
                </label><br/>
                <label>
                    Name:
                    <input type="text" value={this.state.name} onChange={this.handleNameChange} />
                </label><br/>
                <label>
                    Passwd:
                    <input type="password" value={this.state.passwd} onChange={this.handlePasswdChange} />
                </label><br/>

                <input type="submit" value="Submit" />
            </form>
        );
    }
}
export default UserForm;