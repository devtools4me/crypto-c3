package me.devtools4.crypto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class CompletableExecutor {
  private final Executor executor;

  public CompletableExecutor(Executor executor) {
    this.executor = executor;
  }

  public <T> CompletableFuture<T> execute(Consumer<CompletableFuture<T>> cb) {
    CompletableFuture<T> f = new CompletableFuture<>();
    executor.execute(() -> cb.accept(f));
    return f;
  }
}