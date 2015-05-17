inherit image

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

IMAGE_PREPROCESS_COMMAND = "rootfs_update_timestamp ;"

CONMANPKGS ?= "connman connman-angstrom-settings connman-plugin-loopback \
        connman-plugin-ethernet connman-systemd"
CONMANPKGS_libc-uclibc = ""

IMAGE_INSTALL += " \
	angstrom-packagegroup-boot \
	packagegroup-basic \
	${CONMANPKGS} \
  tzdata cpufrequtils \
	timestamp-service \
  openssh-sshd \
  openssh-sftp-server \
  rsync \
  perf perf-perl perf-python perf-archive \
  xz \
  tar \
  less \
  coreutils \
  gdb gdbserver \
  lttng-tools lttng-modules \
  e2fsprogs e2fsprogs-e2fsck e2fsprogs-mke2fs e2fsprogs-badblocks \
  bash \
"

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

export IMAGE_BASENAME = "trace-image"

ROOTFS_POSTPROCESS_COMMAND =+ " \
  echo 'tracelab' > $D/${sysconfdir}/hostname; \
  echo '127.0.1.1 tracelab.localdomain tracelab' >> $D/${sysconfdir}/hosts; \
  rm -f $D/${sysconfdir}/system/sockets.target.wants/sshd.socket; \
  ln -sf /lib/systemd/system/sshd.service $D${sysconfdir}/systemd/system/multi-user.target.wants/; \
  echo "PermitEmptyPasswords yes" >> $D${sysconfdir}/ssh/sshd_config; \
  "
