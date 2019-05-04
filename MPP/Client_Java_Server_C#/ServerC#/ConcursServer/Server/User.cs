/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;

namespace Concurs
{

  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class User : TBase
  {
    private string _username;
    private string _password;
    private TipUser _tipUser;

    public string Username
    {
      get
      {
        return _username;
      }
      set
      {
        __isset.username = true;
        this._username = value;
      }
    }

    public string Password
    {
      get
      {
        return _password;
      }
      set
      {
        __isset.password = true;
        this._password = value;
      }
    }

    /// <summary>
    /// 
    /// <seealso cref="TipUser"/>
    /// </summary>
    public TipUser TipUser
    {
      get
      {
        return _tipUser;
      }
      set
      {
        __isset.tipUser = true;
        this._tipUser = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool username;
      public bool password;
      public bool tipUser;
    }

    public User() {
    }

    public User(string username, string password, TipUser tipUser)
        {
            __isset.username = true;
            this._username = username;
            __isset.password = true;
            this._password = password;
            __isset.tipUser = true;
            this._tipUser = tipUser;

        }

        public User(string username, string password)
        {
            __isset.username = true;
            this._username = username;
            __isset.password = true;
            this._password = password;
            __isset.tipUser = true;
            this._tipUser = TipUser.ADMIN;

        }

        public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        TField field;
        iprot.ReadStructBegin();
        while (true)
        {
          field = iprot.ReadFieldBegin();
          if (field.Type == TType.Stop) { 
            break;
          }
          switch (field.ID)
          {
            case 1:
              if (field.Type == TType.String) {
                Username = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.String) {
                Password = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.I32) {
                TipUser = (TipUser)iprot.ReadI32();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            default: 
              TProtocolUtil.Skip(iprot, field.Type);
              break;
          }
          iprot.ReadFieldEnd();
        }
        iprot.ReadStructEnd();
      }
      finally
      {
        iprot.DecrementRecursionDepth();
      }
    }

    public void Write(TProtocol oprot) {
      oprot.IncrementRecursionDepth();
      try
      {
        TStruct struc = new TStruct("User");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        if (Username != null && __isset.username) {
          field.Name = "username";
          field.Type = TType.String;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Username);
          oprot.WriteFieldEnd();
        }
        if (Password != null && __isset.password) {
          field.Name = "password";
          field.Type = TType.String;
          field.ID = 2;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Password);
          oprot.WriteFieldEnd();
        }
        if (__isset.tipUser) {
          field.Name = "tipUser";
          field.Type = TType.I32;
          field.ID = 3;
          oprot.WriteFieldBegin(field);
          oprot.WriteI32((int)TipUser);
          oprot.WriteFieldEnd();
        }
        oprot.WriteFieldStop();
        oprot.WriteStructEnd();
      }
      finally
      {
        oprot.DecrementRecursionDepth();
      }
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("User(");
      bool __first = true;
      if (Username != null && __isset.username) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Username: ");
        __sb.Append(Username);
      }
      if (Password != null && __isset.password) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Password: ");
        __sb.Append(Password);
      }
      if (__isset.tipUser) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("TipUser: ");
        __sb.Append(TipUser);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
