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
  public partial class ProbaDTO : TBase
  {
    private int _id;
    private string _denumire;
    private string _categorie;
    private int _nrParticipanti;

    public int Id
    {
      get
      {
        return _id;
      }
      set
      {
        __isset.id = true;
        this._id = value;
      }
    }

    public string Denumire
    {
      get
      {
        return _denumire;
      }
      set
      {
        __isset.denumire = true;
        this._denumire = value;
      }
    }

    public string Categorie
    {
      get
      {
        return _categorie;
      }
      set
      {
        __isset.categorie = true;
        this._categorie = value;
      }
    }

    public int NrParticipanti
    {
      get
      {
        return _nrParticipanti;
      }
      set
      {
        __isset.nrParticipanti = true;
        this._nrParticipanti = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool id;
      public bool denumire;
      public bool categorie;
      public bool nrParticipanti;
    }

    public ProbaDTO() {
    }

    public ProbaDTO(int id, string denumire, string categorie, int nrParticipanti)
    {
        __isset.id = true;
        this._id = id;

        __isset.denumire = true;
        this._denumire = denumire;

        __isset.categorie = true;
        this._categorie = categorie;

        __isset.nrParticipanti = true;
        this._nrParticipanti = nrParticipanti;
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
              if (field.Type == TType.I32) {
                Id = iprot.ReadI32();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.String) {
                Denumire = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                Categorie = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.I32) {
                NrParticipanti = iprot.ReadI32();
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
        TStruct struc = new TStruct("ProbaDTO");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        if (__isset.id) {
          field.Name = "id";
          field.Type = TType.I32;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          oprot.WriteI32(Id);
          oprot.WriteFieldEnd();
        }
        if (Denumire != null && __isset.denumire) {
          field.Name = "denumire";
          field.Type = TType.String;
          field.ID = 2;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Denumire);
          oprot.WriteFieldEnd();
        }
        if (Categorie != null && __isset.categorie) {
          field.Name = "categorie";
          field.Type = TType.String;
          field.ID = 3;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Categorie);
          oprot.WriteFieldEnd();
        }
        if (__isset.nrParticipanti) {
          field.Name = "nrParticipanti";
          field.Type = TType.I32;
          field.ID = 4;
          oprot.WriteFieldBegin(field);
          oprot.WriteI32(NrParticipanti);
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
      StringBuilder __sb = new StringBuilder("ProbaDTO(");
      bool __first = true;
      if (__isset.id) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Id: ");
        __sb.Append(Id);
      }
      if (Denumire != null && __isset.denumire) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Denumire: ");
        __sb.Append(Denumire);
      }
      if (Categorie != null && __isset.categorie) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Categorie: ");
        __sb.Append(Categorie);
      }
      if (__isset.nrParticipanti) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("NrParticipanti: ");
        __sb.Append(NrParticipanti);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
