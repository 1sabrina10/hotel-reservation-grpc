package org.example.hotel.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service de consultation d'hôtel
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: hotel_consultation.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HotelConsultationServiceGrpc {

  private HotelConsultationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "org.example.hotel.HotelConsultationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.hotel.grpc.AuthRequest,
      org.example.hotel.grpc.AuthResponse> getAuthentifierMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Authentifier",
      requestType = org.example.hotel.grpc.AuthRequest.class,
      responseType = org.example.hotel.grpc.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.hotel.grpc.AuthRequest,
      org.example.hotel.grpc.AuthResponse> getAuthentifierMethod() {
    io.grpc.MethodDescriptor<org.example.hotel.grpc.AuthRequest, org.example.hotel.grpc.AuthResponse> getAuthentifierMethod;
    if ((getAuthentifierMethod = HotelConsultationServiceGrpc.getAuthentifierMethod) == null) {
      synchronized (HotelConsultationServiceGrpc.class) {
        if ((getAuthentifierMethod = HotelConsultationServiceGrpc.getAuthentifierMethod) == null) {
          HotelConsultationServiceGrpc.getAuthentifierMethod = getAuthentifierMethod =
              io.grpc.MethodDescriptor.<org.example.hotel.grpc.AuthRequest, org.example.hotel.grpc.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Authentifier"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.AuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.AuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HotelConsultationServiceMethodDescriptorSupplier("Authentifier"))
              .build();
        }
      }
    }
    return getAuthentifierMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.hotel.grpc.DisponibiliteRequest,
      org.example.hotel.grpc.DisponibiliteResponse> getConsulterDisponibilitesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConsulterDisponibilites",
      requestType = org.example.hotel.grpc.DisponibiliteRequest.class,
      responseType = org.example.hotel.grpc.DisponibiliteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.hotel.grpc.DisponibiliteRequest,
      org.example.hotel.grpc.DisponibiliteResponse> getConsulterDisponibilitesMethod() {
    io.grpc.MethodDescriptor<org.example.hotel.grpc.DisponibiliteRequest, org.example.hotel.grpc.DisponibiliteResponse> getConsulterDisponibilitesMethod;
    if ((getConsulterDisponibilitesMethod = HotelConsultationServiceGrpc.getConsulterDisponibilitesMethod) == null) {
      synchronized (HotelConsultationServiceGrpc.class) {
        if ((getConsulterDisponibilitesMethod = HotelConsultationServiceGrpc.getConsulterDisponibilitesMethod) == null) {
          HotelConsultationServiceGrpc.getConsulterDisponibilitesMethod = getConsulterDisponibilitesMethod =
              io.grpc.MethodDescriptor.<org.example.hotel.grpc.DisponibiliteRequest, org.example.hotel.grpc.DisponibiliteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConsulterDisponibilites"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.DisponibiliteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.DisponibiliteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HotelConsultationServiceMethodDescriptorSupplier("ConsulterDisponibilites"))
              .build();
        }
      }
    }
    return getConsulterDisponibilitesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelReservationRequest,
      org.example.hotel.grpc.HotelReservationResponse> getReserverChambreAutoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserverChambreAuto",
      requestType = org.example.hotel.grpc.HotelReservationRequest.class,
      responseType = org.example.hotel.grpc.HotelReservationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelReservationRequest,
      org.example.hotel.grpc.HotelReservationResponse> getReserverChambreAutoMethod() {
    io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelReservationRequest, org.example.hotel.grpc.HotelReservationResponse> getReserverChambreAutoMethod;
    if ((getReserverChambreAutoMethod = HotelConsultationServiceGrpc.getReserverChambreAutoMethod) == null) {
      synchronized (HotelConsultationServiceGrpc.class) {
        if ((getReserverChambreAutoMethod = HotelConsultationServiceGrpc.getReserverChambreAutoMethod) == null) {
          HotelConsultationServiceGrpc.getReserverChambreAutoMethod = getReserverChambreAutoMethod =
              io.grpc.MethodDescriptor.<org.example.hotel.grpc.HotelReservationRequest, org.example.hotel.grpc.HotelReservationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserverChambreAuto"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.HotelReservationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.HotelReservationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HotelConsultationServiceMethodDescriptorSupplier("ReserverChambreAuto"))
              .build();
        }
      }
    }
    return getReserverChambreAutoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelAnnulerReservationRequest,
      org.example.hotel.grpc.HotelAnnulerReservationResponse> getAnnulerReservationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AnnulerReservation",
      requestType = org.example.hotel.grpc.HotelAnnulerReservationRequest.class,
      responseType = org.example.hotel.grpc.HotelAnnulerReservationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelAnnulerReservationRequest,
      org.example.hotel.grpc.HotelAnnulerReservationResponse> getAnnulerReservationMethod() {
    io.grpc.MethodDescriptor<org.example.hotel.grpc.HotelAnnulerReservationRequest, org.example.hotel.grpc.HotelAnnulerReservationResponse> getAnnulerReservationMethod;
    if ((getAnnulerReservationMethod = HotelConsultationServiceGrpc.getAnnulerReservationMethod) == null) {
      synchronized (HotelConsultationServiceGrpc.class) {
        if ((getAnnulerReservationMethod = HotelConsultationServiceGrpc.getAnnulerReservationMethod) == null) {
          HotelConsultationServiceGrpc.getAnnulerReservationMethod = getAnnulerReservationMethod =
              io.grpc.MethodDescriptor.<org.example.hotel.grpc.HotelAnnulerReservationRequest, org.example.hotel.grpc.HotelAnnulerReservationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnnulerReservation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.HotelAnnulerReservationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.hotel.grpc.HotelAnnulerReservationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HotelConsultationServiceMethodDescriptorSupplier("AnnulerReservation"))
              .build();
        }
      }
    }
    return getAnnulerReservationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HotelConsultationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceStub>() {
        @java.lang.Override
        public HotelConsultationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelConsultationServiceStub(channel, callOptions);
        }
      };
    return HotelConsultationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HotelConsultationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceBlockingStub>() {
        @java.lang.Override
        public HotelConsultationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelConsultationServiceBlockingStub(channel, callOptions);
        }
      };
    return HotelConsultationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HotelConsultationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelConsultationServiceFutureStub>() {
        @java.lang.Override
        public HotelConsultationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelConsultationServiceFutureStub(channel, callOptions);
        }
      };
    return HotelConsultationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service de consultation d'hôtel
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Authentification d'une agence
     * </pre>
     */
    default void authentifier(org.example.hotel.grpc.AuthRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.AuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthentifierMethod(), responseObserver);
    }

    /**
     * <pre>
     * Consulter les disponibilités
     * </pre>
     */
    default void consulterDisponibilites(org.example.hotel.grpc.DisponibiliteRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.DisponibiliteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConsulterDisponibilitesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Réserver une chambre automatiquement
     * </pre>
     */
    default void reserverChambreAuto(org.example.hotel.grpc.HotelReservationRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelReservationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReserverChambreAutoMethod(), responseObserver);
    }

    /**
     * <pre>
     * Annuler une réservation
     * </pre>
     */
    default void annulerReservation(org.example.hotel.grpc.HotelAnnulerReservationRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelAnnulerReservationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAnnulerReservationMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HotelConsultationService.
   * <pre>
   * Service de consultation d'hôtel
   * </pre>
   */
  public static abstract class HotelConsultationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HotelConsultationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HotelConsultationService.
   * <pre>
   * Service de consultation d'hôtel
   * </pre>
   */
  public static final class HotelConsultationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HotelConsultationServiceStub> {
    private HotelConsultationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelConsultationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelConsultationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authentification d'une agence
     * </pre>
     */
    public void authentifier(org.example.hotel.grpc.AuthRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.AuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthentifierMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Consulter les disponibilités
     * </pre>
     */
    public void consulterDisponibilites(org.example.hotel.grpc.DisponibiliteRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.DisponibiliteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConsulterDisponibilitesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Réserver une chambre automatiquement
     * </pre>
     */
    public void reserverChambreAuto(org.example.hotel.grpc.HotelReservationRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelReservationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReserverChambreAutoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Annuler une réservation
     * </pre>
     */
    public void annulerReservation(org.example.hotel.grpc.HotelAnnulerReservationRequest request,
        io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelAnnulerReservationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAnnulerReservationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HotelConsultationService.
   * <pre>
   * Service de consultation d'hôtel
   * </pre>
   */
  public static final class HotelConsultationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HotelConsultationServiceBlockingStub> {
    private HotelConsultationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelConsultationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelConsultationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authentification d'une agence
     * </pre>
     */
    public org.example.hotel.grpc.AuthResponse authentifier(org.example.hotel.grpc.AuthRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthentifierMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Consulter les disponibilités
     * </pre>
     */
    public org.example.hotel.grpc.DisponibiliteResponse consulterDisponibilites(org.example.hotel.grpc.DisponibiliteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConsulterDisponibilitesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Réserver une chambre automatiquement
     * </pre>
     */
    public org.example.hotel.grpc.HotelReservationResponse reserverChambreAuto(org.example.hotel.grpc.HotelReservationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReserverChambreAutoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Annuler une réservation
     * </pre>
     */
    public org.example.hotel.grpc.HotelAnnulerReservationResponse annulerReservation(org.example.hotel.grpc.HotelAnnulerReservationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAnnulerReservationMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HotelConsultationService.
   * <pre>
   * Service de consultation d'hôtel
   * </pre>
   */
  public static final class HotelConsultationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HotelConsultationServiceFutureStub> {
    private HotelConsultationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelConsultationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelConsultationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authentification d'une agence
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.hotel.grpc.AuthResponse> authentifier(
        org.example.hotel.grpc.AuthRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthentifierMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Consulter les disponibilités
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.hotel.grpc.DisponibiliteResponse> consulterDisponibilites(
        org.example.hotel.grpc.DisponibiliteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConsulterDisponibilitesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Réserver une chambre automatiquement
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.hotel.grpc.HotelReservationResponse> reserverChambreAuto(
        org.example.hotel.grpc.HotelReservationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReserverChambreAutoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Annuler une réservation
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.hotel.grpc.HotelAnnulerReservationResponse> annulerReservation(
        org.example.hotel.grpc.HotelAnnulerReservationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAnnulerReservationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTIFIER = 0;
  private static final int METHODID_CONSULTER_DISPONIBILITES = 1;
  private static final int METHODID_RESERVER_CHAMBRE_AUTO = 2;
  private static final int METHODID_ANNULER_RESERVATION = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTIFIER:
          serviceImpl.authentifier((org.example.hotel.grpc.AuthRequest) request,
              (io.grpc.stub.StreamObserver<org.example.hotel.grpc.AuthResponse>) responseObserver);
          break;
        case METHODID_CONSULTER_DISPONIBILITES:
          serviceImpl.consulterDisponibilites((org.example.hotel.grpc.DisponibiliteRequest) request,
              (io.grpc.stub.StreamObserver<org.example.hotel.grpc.DisponibiliteResponse>) responseObserver);
          break;
        case METHODID_RESERVER_CHAMBRE_AUTO:
          serviceImpl.reserverChambreAuto((org.example.hotel.grpc.HotelReservationRequest) request,
              (io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelReservationResponse>) responseObserver);
          break;
        case METHODID_ANNULER_RESERVATION:
          serviceImpl.annulerReservation((org.example.hotel.grpc.HotelAnnulerReservationRequest) request,
              (io.grpc.stub.StreamObserver<org.example.hotel.grpc.HotelAnnulerReservationResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAuthentifierMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.example.hotel.grpc.AuthRequest,
              org.example.hotel.grpc.AuthResponse>(
                service, METHODID_AUTHENTIFIER)))
        .addMethod(
          getConsulterDisponibilitesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.example.hotel.grpc.DisponibiliteRequest,
              org.example.hotel.grpc.DisponibiliteResponse>(
                service, METHODID_CONSULTER_DISPONIBILITES)))
        .addMethod(
          getReserverChambreAutoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.example.hotel.grpc.HotelReservationRequest,
              org.example.hotel.grpc.HotelReservationResponse>(
                service, METHODID_RESERVER_CHAMBRE_AUTO)))
        .addMethod(
          getAnnulerReservationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.example.hotel.grpc.HotelAnnulerReservationRequest,
              org.example.hotel.grpc.HotelAnnulerReservationResponse>(
                service, METHODID_ANNULER_RESERVATION)))
        .build();
  }

  private static abstract class HotelConsultationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HotelConsultationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.hotel.grpc.HotelConsultationProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HotelConsultationService");
    }
  }

  private static final class HotelConsultationServiceFileDescriptorSupplier
      extends HotelConsultationServiceBaseDescriptorSupplier {
    HotelConsultationServiceFileDescriptorSupplier() {}
  }

  private static final class HotelConsultationServiceMethodDescriptorSupplier
      extends HotelConsultationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HotelConsultationServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HotelConsultationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HotelConsultationServiceFileDescriptorSupplier())
              .addMethod(getAuthentifierMethod())
              .addMethod(getConsulterDisponibilitesMethod())
              .addMethod(getReserverChambreAutoMethod())
              .addMethod(getAnnulerReservationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
