// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConcatVectorTable.proto

package io.github.keenon.loglinear;

public final class ConcatVectorTableProto {
  private ConcatVectorTableProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ConcatVectorTableOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // repeated int32 dimensionSize = 1;
    java.util.List<java.lang.Integer> getDimensionSizeList();
    int getDimensionSizeCount();
    int getDimensionSize(int index);
    
    // repeated .io.github.keenon.ConcatVector factorTable = 2;
    java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> 
        getFactorTableList();
    io.github.keenon.loglinear.ConcatVectorProto.ConcatVector getFactorTable(int index);
    int getFactorTableCount();
    java.util.List<? extends io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder> 
        getFactorTableOrBuilderList();
    io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder getFactorTableOrBuilder(
        int index);
  }
  public static final class ConcatVectorTable extends
      com.google.protobuf.GeneratedMessage
      implements ConcatVectorTableOrBuilder {
    // Use ConcatVectorTable.newBuilder() to construct.
    private ConcatVectorTable(Builder builder) {
      super(builder);
    }
    private ConcatVectorTable(boolean noInit) {}
    
    private static final ConcatVectorTable defaultInstance;
    public static ConcatVectorTable getDefaultInstance() {
      return defaultInstance;
    }
    
    public ConcatVectorTable getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.github.keenon.loglinear.ConcatVectorTableProto.internal_static_io_github_keenon_ConcatVectorTable_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.github.keenon.loglinear.ConcatVectorTableProto.internal_static_io_github_keenon_ConcatVectorTable_fieldAccessorTable;
    }
    
    // repeated int32 dimensionSize = 1;
    public static final int DIMENSIONSIZE_FIELD_NUMBER = 1;
    private java.util.List<java.lang.Integer> dimensionSize_;
    public java.util.List<java.lang.Integer>
        getDimensionSizeList() {
      return dimensionSize_;
    }
    public int getDimensionSizeCount() {
      return dimensionSize_.size();
    }
    public int getDimensionSize(int index) {
      return dimensionSize_.get(index);
    }
    
    // repeated .io.github.keenon.ConcatVector factorTable = 2;
    public static final int FACTORTABLE_FIELD_NUMBER = 2;
    private java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> factorTable_;
    public java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> getFactorTableList() {
      return factorTable_;
    }
    public java.util.List<? extends io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder> 
        getFactorTableOrBuilderList() {
      return factorTable_;
    }
    public int getFactorTableCount() {
      return factorTable_.size();
    }
    public io.github.keenon.loglinear.ConcatVectorProto.ConcatVector getFactorTable(int index) {
      return factorTable_.get(index);
    }
    public io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder getFactorTableOrBuilder(
        int index) {
      return factorTable_.get(index);
    }
    
    private void initFields() {
      dimensionSize_ = java.util.Collections.emptyList();;
      factorTable_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      for (int i = 0; i < getFactorTableCount(); i++) {
        if (!getFactorTable(i).isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < dimensionSize_.size(); i++) {
        output.writeInt32(1, dimensionSize_.get(i));
      }
      for (int i = 0; i < factorTable_.size(); i++) {
        output.writeMessage(2, factorTable_.get(i));
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < dimensionSize_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dimensionSize_.get(i));
        }
        size += dataSize;
        size += 1 * getDimensionSizeList().size();
      }
      for (int i = 0; i < factorTable_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, factorTable_.get(i));
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTableOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return io.github.keenon.loglinear.ConcatVectorTableProto.internal_static_io_github_keenon_ConcatVectorTable_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return io.github.keenon.loglinear.ConcatVectorTableProto.internal_static_io_github_keenon_ConcatVectorTable_fieldAccessorTable;
      }
      
      // Construct using io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getFactorTableFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        dimensionSize_ = java.util.Collections.emptyList();;
        bitField0_ = (bitField0_ & ~0x00000001);
        if (factorTableBuilder_ == null) {
          factorTable_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          factorTableBuilder_.clear();
        }
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.getDescriptor();
      }
      
      public io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable getDefaultInstanceForType() {
        return io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.getDefaultInstance();
      }
      
      public io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable build() {
        io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable buildPartial() {
        io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable result = new io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          dimensionSize_ = java.util.Collections.unmodifiableList(dimensionSize_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.dimensionSize_ = dimensionSize_;
        if (factorTableBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002)) {
            factorTable_ = java.util.Collections.unmodifiableList(factorTable_);
            bitField0_ = (bitField0_ & ~0x00000002);
          }
          result.factorTable_ = factorTable_;
        } else {
          result.factorTable_ = factorTableBuilder_.build();
        }
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable) {
          return mergeFrom((io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable other) {
        if (other == io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.getDefaultInstance()) return this;
        if (!other.dimensionSize_.isEmpty()) {
          if (dimensionSize_.isEmpty()) {
            dimensionSize_ = other.dimensionSize_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureDimensionSizeIsMutable();
            dimensionSize_.addAll(other.dimensionSize_);
          }
          onChanged();
        }
        if (factorTableBuilder_ == null) {
          if (!other.factorTable_.isEmpty()) {
            if (factorTable_.isEmpty()) {
              factorTable_ = other.factorTable_;
              bitField0_ = (bitField0_ & ~0x00000002);
            } else {
              ensureFactorTableIsMutable();
              factorTable_.addAll(other.factorTable_);
            }
            onChanged();
          }
        } else {
          if (!other.factorTable_.isEmpty()) {
            if (factorTableBuilder_.isEmpty()) {
              factorTableBuilder_.dispose();
              factorTableBuilder_ = null;
              factorTable_ = other.factorTable_;
              bitField0_ = (bitField0_ & ~0x00000002);
              factorTableBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getFactorTableFieldBuilder() : null;
            } else {
              factorTableBuilder_.addAllMessages(other.factorTable_);
            }
          }
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        for (int i = 0; i < getFactorTableCount(); i++) {
          if (!getFactorTable(i).isInitialized()) {
            
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              ensureDimensionSizeIsMutable();
              dimensionSize_.add(input.readInt32());
              break;
            }
            case 10: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              while (input.getBytesUntilLimit() > 0) {
                addDimensionSize(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
            case 18: {
              io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder subBuilder = io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addFactorTable(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // repeated int32 dimensionSize = 1;
      private java.util.List<java.lang.Integer> dimensionSize_ = java.util.Collections.emptyList();;
      private void ensureDimensionSizeIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          dimensionSize_ = new java.util.ArrayList<java.lang.Integer>(dimensionSize_);
          bitField0_ |= 0x00000001;
         }
      }
      public java.util.List<java.lang.Integer>
          getDimensionSizeList() {
        return java.util.Collections.unmodifiableList(dimensionSize_);
      }
      public int getDimensionSizeCount() {
        return dimensionSize_.size();
      }
      public int getDimensionSize(int index) {
        return dimensionSize_.get(index);
      }
      public Builder setDimensionSize(
          int index, int value) {
        ensureDimensionSizeIsMutable();
        dimensionSize_.set(index, value);
        onChanged();
        return this;
      }
      public Builder addDimensionSize(int value) {
        ensureDimensionSizeIsMutable();
        dimensionSize_.add(value);
        onChanged();
        return this;
      }
      public Builder addAllDimensionSize(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureDimensionSizeIsMutable();
        super.addAll(values, dimensionSize_);
        onChanged();
        return this;
      }
      public Builder clearDimensionSize() {
        dimensionSize_ = java.util.Collections.emptyList();;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      
      // repeated .io.github.keenon.ConcatVector factorTable = 2;
      private java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> factorTable_ =
        java.util.Collections.emptyList();
      private void ensureFactorTableIsMutable() {
        if (!((bitField0_ & 0x00000002) == 0x00000002)) {
          factorTable_ = new java.util.ArrayList<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector>(factorTable_);
          bitField0_ |= 0x00000002;
         }
      }
      
      private com.google.protobuf.RepeatedFieldBuilder<
          io.github.keenon.loglinear.ConcatVectorProto.ConcatVector, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder, io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder> factorTableBuilder_;
      
      public java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> getFactorTableList() {
        if (factorTableBuilder_ == null) {
          return java.util.Collections.unmodifiableList(factorTable_);
        } else {
          return factorTableBuilder_.getMessageList();
        }
      }
      public int getFactorTableCount() {
        if (factorTableBuilder_ == null) {
          return factorTable_.size();
        } else {
          return factorTableBuilder_.getCount();
        }
      }
      public io.github.keenon.loglinear.ConcatVectorProto.ConcatVector getFactorTable(int index) {
        if (factorTableBuilder_ == null) {
          return factorTable_.get(index);
        } else {
          return factorTableBuilder_.getMessage(index);
        }
      }
      public Builder setFactorTable(
          int index, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector value) {
        if (factorTableBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFactorTableIsMutable();
          factorTable_.set(index, value);
          onChanged();
        } else {
          factorTableBuilder_.setMessage(index, value);
        }
        return this;
      }
      public Builder setFactorTable(
          int index, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder builderForValue) {
        if (factorTableBuilder_ == null) {
          ensureFactorTableIsMutable();
          factorTable_.set(index, builderForValue.build());
          onChanged();
        } else {
          factorTableBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      public Builder addFactorTable(io.github.keenon.loglinear.ConcatVectorProto.ConcatVector value) {
        if (factorTableBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFactorTableIsMutable();
          factorTable_.add(value);
          onChanged();
        } else {
          factorTableBuilder_.addMessage(value);
        }
        return this;
      }
      public Builder addFactorTable(
          int index, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector value) {
        if (factorTableBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFactorTableIsMutable();
          factorTable_.add(index, value);
          onChanged();
        } else {
          factorTableBuilder_.addMessage(index, value);
        }
        return this;
      }
      public Builder addFactorTable(
          io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder builderForValue) {
        if (factorTableBuilder_ == null) {
          ensureFactorTableIsMutable();
          factorTable_.add(builderForValue.build());
          onChanged();
        } else {
          factorTableBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      public Builder addFactorTable(
          int index, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder builderForValue) {
        if (factorTableBuilder_ == null) {
          ensureFactorTableIsMutable();
          factorTable_.add(index, builderForValue.build());
          onChanged();
        } else {
          factorTableBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      public Builder addAllFactorTable(
          java.lang.Iterable<? extends io.github.keenon.loglinear.ConcatVectorProto.ConcatVector> values) {
        if (factorTableBuilder_ == null) {
          ensureFactorTableIsMutable();
          super.addAll(values, factorTable_);
          onChanged();
        } else {
          factorTableBuilder_.addAllMessages(values);
        }
        return this;
      }
      public Builder clearFactorTable() {
        if (factorTableBuilder_ == null) {
          factorTable_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000002);
          onChanged();
        } else {
          factorTableBuilder_.clear();
        }
        return this;
      }
      public Builder removeFactorTable(int index) {
        if (factorTableBuilder_ == null) {
          ensureFactorTableIsMutable();
          factorTable_.remove(index);
          onChanged();
        } else {
          factorTableBuilder_.remove(index);
        }
        return this;
      }
      public io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder getFactorTableBuilder(
          int index) {
        return getFactorTableFieldBuilder().getBuilder(index);
      }
      public io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder getFactorTableOrBuilder(
          int index) {
        if (factorTableBuilder_ == null) {
          return factorTable_.get(index);  } else {
          return factorTableBuilder_.getMessageOrBuilder(index);
        }
      }
      public java.util.List<? extends io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder> 
           getFactorTableOrBuilderList() {
        if (factorTableBuilder_ != null) {
          return factorTableBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(factorTable_);
        }
      }
      public io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder addFactorTableBuilder() {
        return getFactorTableFieldBuilder().addBuilder(
            io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.getDefaultInstance());
      }
      public io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder addFactorTableBuilder(
          int index) {
        return getFactorTableFieldBuilder().addBuilder(
            index, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.getDefaultInstance());
      }
      public java.util.List<io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder> 
           getFactorTableBuilderList() {
        return getFactorTableFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          io.github.keenon.loglinear.ConcatVectorProto.ConcatVector, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder, io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder> 
          getFactorTableFieldBuilder() {
        if (factorTableBuilder_ == null) {
          factorTableBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              io.github.keenon.loglinear.ConcatVectorProto.ConcatVector, io.github.keenon.loglinear.ConcatVectorProto.ConcatVector.Builder, io.github.keenon.loglinear.ConcatVectorProto.ConcatVectorOrBuilder>(
                  factorTable_,
                  ((bitField0_ & 0x00000002) == 0x00000002),
                  getParentForChildren(),
                  isClean());
          factorTable_ = null;
        }
        return factorTableBuilder_;
      }
      
      // @@protoc_insertion_point(builder_scope:io.github.keenon.ConcatVectorTable)
    }
    
    static {
      defaultInstance = new ConcatVectorTable(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:io.github.keenon.ConcatVectorTable)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_io_github_keenon_ConcatVectorTable_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_io_github_keenon_ConcatVectorTable_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027ConcatVectorTable.proto\022\020io.github.kee" +
      "non\032\022ConcatVector.proto\"_\n\021ConcatVectorT" +
      "able\022\025\n\rdimensionSize\030\001 \003(\005\0223\n\013factorTab" +
      "le\030\002 \003(\0132\036.io.github.keenon.ConcatVector" +
      "B4\n\032io.github.keenon.loglinearB\026ConcatVe" +
      "ctorTableProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_io_github_keenon_ConcatVectorTable_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_io_github_keenon_ConcatVectorTable_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_io_github_keenon_ConcatVectorTable_descriptor,
              new java.lang.String[] { "DimensionSize", "FactorTable", },
              io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.class,
              io.github.keenon.loglinear.ConcatVectorTableProto.ConcatVectorTable.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          io.github.keenon.loglinear.ConcatVectorProto.getDescriptor(),
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}